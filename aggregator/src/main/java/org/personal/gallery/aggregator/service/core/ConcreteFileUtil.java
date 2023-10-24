package org.personal.gallery.aggregator.service.core;

import org.personal.gallery.aggregator.service.ports.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class ConcreteFileUtil implements FileUtil {

    private static final  String FILE_PATH = "classpath:terms.txt";

    @Override
    public List<String> readAllContentByLines() {
        try {
            File resource = ResourceUtils.getFile(FILE_PATH);
            return Files.readAllLines(resource.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeTermToFile(String term) {

        try {
            File resource  = ResourceUtils.getFile(FILE_PATH);
            Files.writeString(resource.toPath(),term);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
