package org.personal.gallery.aggregator.service.core;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.personal.gallery.aggregator.service.core.models.TrieNode;
import org.personal.gallery.aggregator.service.core.models.WordFrequency;
import org.personal.gallery.aggregator.service.ports.Trie;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConcreteTrie implements Trie {
    private static final String PREFIX_INFO = "prefix_info";
    private final TrieNode root;
    private final Jedis jedis;
    private final Gson gson;

    public ConcreteTrie() {
        root = TrieNode.getNewTrieNode();
        gson = new Gson();
        jedis = new Jedis("localhost", 6379);
    }



    public void insertWord(String word, int frequency) {
        TrieNode node = root;
        StringBuilder runningPrefix = new StringBuilder();
        for (char c : word.toCharArray()) {
            runningPrefix.append(c);
            node.getChildren().putIfAbsent(c, new TrieNode());
            node = node.getChildren().get(c);

            node.getWordList()
                    .getOrDefault(runningPrefix.toString(), WordFrequency.from(runningPrefix,
                    0)).addFrequency(frequency);

        }
        node.getWordList()
                .getOrDefault(runningPrefix.toString(), new WordFrequency(runningPrefix.toString(), 0))
                .addFrequency(frequency);
    }

    @Override
    public List<WordFrequency> retrievePrefixInfoFromRedis(String prefix) {
        String jsonString = jedis.hget(PREFIX_INFO, prefix);

        return gson.fromJson(jsonString, new TypeToken<List<WordFrequency>>() {
        }.getType());
    }

    @Override
    public void precomputeAndStorePrefixesInRedis() {
        Map<String, List<WordFrequency>> prefixInfo = new HashMap<>();
        precomputePrefixes(root, "", prefixInfo);

        Pipeline pipeline = jedis.pipelined();
        for (Map.Entry<String, List<WordFrequency>> entry : prefixInfo.entrySet()) {
            String prefix = entry.getKey();
            List<WordFrequency> words = entry.getValue();
            pipeline.hset(PREFIX_INFO, prefix, toJson(words));
        }
        pipeline.sync();
    }

    private void precomputePrefixes(TrieNode node, String prefix, Map<String, List<WordFrequency>> prefixInfo) {
        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            char c = entry.getKey();
            TrieNode child = entry.getValue();
            String newPrefix = prefix + c;

            if (!child.getWordList().isEmpty()) {
                prefixInfo.put(newPrefix, child.getWordList().values().stream().toList());
            }
            precomputePrefixes(child, newPrefix, prefixInfo);
        }
    }

    private String toJson(List<WordFrequency> wordList) {

        return gson.toJson(wordList);

//        StringBuilder json = new StringBuilder("[");
//        for (WordFrequency wf : wordList) {
//            json.append("{\"word\":\"").append(wf.word).append("\",\"frequency\":").append(wf.frequency).append("},");
//        }
//        if (json.charAt(json.length() - 1) == ',') {
//            json.setLength(json.length() - 1); // Remove the trailing comma
//        }
//        json.append("]");
//        return json.toString();
    }
}
