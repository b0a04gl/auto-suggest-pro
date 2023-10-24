package org.personal.gallery.aggregator.service.ports;

import java.util.List;

public interface FileUtil {
    List<String> readAllContentByLines();
    void writeTermToFile(String term);
}
