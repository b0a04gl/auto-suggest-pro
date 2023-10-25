package org.personal.gallery.aggregator.service.core;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.personal.gallery.aggregator.service.core.models.TrieNode;
import org.personal.gallery.aggregator.service.core.models.WordFrequency;
import org.personal.gallery.aggregator.service.ports.Redis;
import org.personal.gallery.aggregator.service.ports.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConcreteTrie implements Trie {
    private final TrieNode root;
    private final Gson gson;

    private final Redis redisClient;

    @Autowired
    public ConcreteTrie(Redis redisClient) {
        root = TrieNode.getNewTrieNode();
        gson = new Gson();
        this.redisClient = redisClient;
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
        String jsonString = redisClient.retrieve(prefix);
        return getFromJson(jsonString);
    }

    private List<WordFrequency> getFromJson(String jsonString) {
        return gson.fromJson(jsonString, new TypeToken<List<WordFrequency>>() {
        }.getType());
    }

    @Override
    public void precomputeAndStorePrefixesInRedis() {
        Map<String, List<WordFrequency>> prefixInfo = new HashMap<>();
        precomputePrefixes(root, "", prefixInfo);


        Map<String, String> transformedMap = prefixInfo.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> toJson(entry.getValue())
                ));


        redisClient.store(transformedMap);


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
    }
}
