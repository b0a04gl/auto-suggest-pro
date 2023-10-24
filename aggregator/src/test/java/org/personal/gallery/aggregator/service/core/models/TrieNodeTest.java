package org.personal.gallery.aggregator.service.core.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrieNodeTest {
    private TrieNode trieNode;

    @BeforeEach
    void setUp() {
        trieNode = new TrieNode();
    }

    @Test
    void testConstructor() {
        assertNotNull(trieNode);
        assertTrue(trieNode.getChildren().isEmpty());
        assertTrue(trieNode.getWordList().isEmpty());
    }

    @Test
    void testGetNewTrieNode() {
        TrieNode newTrieNode = TrieNode.getNewTrieNode();
        assertNotNull(newTrieNode);
        assertNotSame(newTrieNode, trieNode); 
        assertTrue(newTrieNode.getChildren().isEmpty());
        assertTrue(newTrieNode.getWordList().isEmpty());
    }

}
