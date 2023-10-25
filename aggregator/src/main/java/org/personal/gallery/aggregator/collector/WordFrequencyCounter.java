package org.personal.gallery.aggregator.collector;

import org.personal.gallery.aggregator.service.core.ConcreteTrie;
import org.personal.gallery.aggregator.service.ports.FileUtil;
import org.personal.gallery.aggregator.service.ports.Trie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WordFrequencyCounter {

    private final Trie concreteTrie;

    private final FileUtil concreteFileUtil;

    private final Logger logger = LoggerFactory.getLogger(WordFrequencyCounter.class);

    @Autowired
    public WordFrequencyCounter(Trie concreteTrie, FileUtil concreteFileUtil) {
        this.concreteTrie = concreteTrie;
        this.concreteFileUtil = concreteFileUtil;
    }

    @Scheduled(fixedRateString = "300000")
    void aggregatedTerms() {

        int numThreads = 4;

        ConcurrentMap<String, AtomicInteger> wordFreq = new ConcurrentHashMap<>();

        Thread[] threads = new Thread[numThreads];

        try {
            List<String> lines = concreteFileUtil.readAllContentByLines();

            for (String line : lines) {
                for (int i = 0; i < numThreads; i++) {
                    threads[i] = new Thread(new WordFrequencyWorker(line, wordFreq));
                    threads[i].start();
                }


                for (int i = 0; i < numThreads; i++) {
                    threads[i].join();
                }
            }

            for (Map.Entry<String, AtomicInteger> entry : wordFreq.entrySet()) {
                concreteTrie.insertWord(entry.getKey(), entry.getValue().get());
            }

            logger.info("added words : {}",wordFreq.size());

            concreteTrie.precomputeAndStorePrefixesInRedis();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

