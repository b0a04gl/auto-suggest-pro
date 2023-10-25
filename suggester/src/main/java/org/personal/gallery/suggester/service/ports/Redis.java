package org.personal.gallery.suggester.service.ports;

import java.util.Map;

public interface Redis {
    void store(Map<String,String> cacheData);

    String retrieve(String key);
}
