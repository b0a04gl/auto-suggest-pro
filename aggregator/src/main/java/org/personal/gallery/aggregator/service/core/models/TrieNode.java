package org.personal.gallery.aggregator.service.core.models;


import org.personal.gallery.aggregator.service.ports.Trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TrieNode {
    private final Map<Character, TrieNode> children;
    private final Map<String,WordFrequency> wordList;

    public TrieNode() {
        children = new HashMap<>();
        wordList = new HashMap<>();
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public Map<String, WordFrequency> getWordList() {
        return wordList;
    }

    public static TrieNode getNewTrieNode(){
        return new TrieNode();
    }
}
