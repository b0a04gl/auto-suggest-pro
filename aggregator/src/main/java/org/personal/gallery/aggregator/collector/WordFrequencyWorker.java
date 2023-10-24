package org.personal.gallery.aggregator.collector;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WordFrequencyWorker implements Runnable {
    private final String text;
    private final ConcurrentMap<String, AtomicInteger> wordFreq;

    public WordFrequencyWorker(String text, ConcurrentMap<String, AtomicInteger> wordFreq) {
        this.text = text;
        this.wordFreq = wordFreq;
    }

    @Override
    public void run() {
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (word.isEmpty()) continue;
            wordFreq.compute(word, (key, value) -> {
                if (value == null) {
                    return new AtomicInteger(1);
                } else {
                    value.incrementAndGet();
                    return value;
                }
            });
        }
    }
}
