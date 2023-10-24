package org.personal.gallery.aggregator.collector;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.gallery.aggregator.service.ports.FileUtil;
import org.personal.gallery.aggregator.service.ports.Trie;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordFrequencyCounterTest {

    @Mock
    private Trie concreteTrie;

    @Mock
    private FileUtil concreteFileUtil;

    @InjectMocks
    private WordFrequencyCounter wordFrequencyCounter;

    @BeforeEach
    public void init() {
        wordFrequencyCounter = new WordFrequencyCounter(concreteTrie, concreteFileUtil);
    }

    @Test
    void testAggregatedTerms() {

        when(concreteFileUtil.readAllContentByLines()).thenReturn(List.of(new String[]{"This is a test.", "Another test."}));
        Mockito.doNothing().when(concreteTrie).insertWord(Mockito.anyString(), Mockito.anyInt());
        Mockito.doNothing().when(concreteTrie).precomputeAndStorePrefixesInRedis();

        wordFrequencyCounter.aggregatedTerms();

        Mockito.verify(concreteTrie, Mockito.times(5)).insertWord(Mockito.anyString(), Mockito.anyInt());
        Mockito.verify(concreteTrie, Mockito.times(1)).precomputeAndStorePrefixesInRedis();
    }
}


