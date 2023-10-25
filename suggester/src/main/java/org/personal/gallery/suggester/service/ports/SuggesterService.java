package org.personal.gallery.suggester.service.ports;

import java.util.List;

public interface SuggesterService {
    public List<String> getSuggestions(String userInput);
}
