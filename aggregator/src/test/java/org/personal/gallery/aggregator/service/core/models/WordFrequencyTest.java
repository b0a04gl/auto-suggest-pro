package org.personal.gallery.aggregator.service.core.models;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyTest {
    private WordFrequency wordFrequency;

    @BeforeEach
    void setUp() {
        wordFrequency = new WordFrequency("apple", 5);
    }

    @Test
    void testConstructor() {
        assertEquals("apple", wordFrequency.getWord());
        assertEquals(5, wordFrequency.getFrequency());
    }

    @Test
    void testEquals() {
        WordFrequency sameWordFrequency = new WordFrequency("apple", 5);
        WordFrequency differentWordFrequency = new WordFrequency("banana", 5);

        assertEquals(wordFrequency, sameWordFrequency);
        assertNotEquals(wordFrequency, differentWordFrequency);
    }

    @Test
    void testHashCode() {
        WordFrequency sameWordFrequency = new WordFrequency("apple", 5);
        WordFrequency differentWordFrequency = new WordFrequency("banana", 5);

        assertEquals(wordFrequency.hashCode(), sameWordFrequency.hashCode());
        assertNotEquals(wordFrequency.hashCode(), differentWordFrequency.hashCode());
    }

    @Test
    void testAddFrequency() {
        wordFrequency.addFrequency(3);
        assertEquals(8, wordFrequency.getFrequency());
    }

    @Test
    void testFrom() {
        StringBuilder wordBuilder = new StringBuilder("orange");
        WordFrequency wordFrequency = WordFrequency.from(wordBuilder, 2);

        assertEquals("orange", wordFrequency.getWord());
        assertEquals(2, wordFrequency.getFrequency());
    }
}
