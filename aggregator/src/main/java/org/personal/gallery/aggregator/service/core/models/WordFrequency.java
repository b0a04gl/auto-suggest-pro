package org.personal.gallery.aggregator.service.core.models;

import java.util.Objects;


public class WordFrequency{

    private final String word;
    private Integer frequency;

    public String getWord() {
        return word;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public WordFrequency(String wordString, Integer frequency) {
        this.word = wordString;
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof WordFrequency that)) return false;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, frequency);
    }

    public static WordFrequency from(StringBuilder word, Integer frequency){
        return  new WordFrequency(word.toString(),frequency);
    }

    public void addFrequency(int frequency) {
        this.frequency+=frequency;
    }
}