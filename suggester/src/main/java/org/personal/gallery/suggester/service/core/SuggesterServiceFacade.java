package org.personal.gallery.suggester.service.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.personal.gallery.suggester.service.core.model.WordFrequency;
import org.personal.gallery.suggester.service.ports.Redis;
import org.personal.gallery.suggester.service.ports.SuggesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class SuggesterServiceFacade implements SuggesterService {

    private final Redis redisClient;
    private final Gson gson;

    @Autowired
    public SuggesterServiceFacade(Redis redisClient) {
        this.redisClient = redisClient;
        gson = new Gson();
    }

    @Override
    public List<String> getSuggestions(String userInput) {

        String responseJson = redisClient.retrieve(userInput);
        return getFromJson(responseJson);
    }

    private List<String> getFromJson(String jsonString) {
        List<WordFrequency> wordFrequencyList = gson.fromJson(jsonString, new TypeToken<List<WordFrequency>>() {
        }.getType());

        if (wordFrequencyList.isEmpty())
            return Collections.emptyList();

        return wordFrequencyList.stream()
                .map(WordFrequency::getWord)
                .filter(Objects::nonNull)
                .toList();

    }
}
