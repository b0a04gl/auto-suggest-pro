package org.personal.gallery.aggregator.collector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class WordFrequencyWorkerTest {
    private ConcurrentMap<String, AtomicInteger> wordFreq;

    @BeforeEach
    void setUp() {
        wordFreq = new ConcurrentHashMap<>();
    }

    @Test
    void testWordFrequencyWorkerIncrementsCount() {
        WordFrequencyWorker worker1 = new WordFrequencyWorker("test test word", wordFreq);


        worker1.run();

        assertEquals(2, wordFreq.get("test").get());
        assertEquals(1, wordFreq.get("word").get());
    }

    @Test
    void testWordFrequencyWorkerWithEmptyText() {
        WordFrequencyWorker worker2 = new WordFrequencyWorker("", wordFreq);

        worker2.run();

        assertEquals(0, wordFreq.size());
        assertFalse(wordFreq.containsKey("test"));
        assertFalse(wordFreq.containsKey("word"));
    }

}
