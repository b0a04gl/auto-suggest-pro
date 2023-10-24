package org.personal.gallery.aggregator.service.ports;

import org.personal.gallery.aggregator.service.core.models.WordFrequency;

import java.util.List;

public interface Trie {
    public void insertWord(String word, int frequency);

    List<WordFrequency> retrievePrefixInfoFromRedis(String prefix);

    void precomputeAndStorePrefixesInRedis();
}
