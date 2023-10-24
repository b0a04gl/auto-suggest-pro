package org.personal.gallery.aggregator.service.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcreteFileUtilTest {

    @Mock
    private ConcreteFileUtil fileUtil;

    @Test
    void testReadAllContentByLines() {

        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("Line 1");
        expectedLines.add("Line 2");

        when(fileUtil.readAllContentByLines()).thenReturn(expectedLines);

        List<String> actualLines = fileUtil.readAllContentByLines();

        assertEquals(expectedLines, actualLines);
        Mockito.verify(fileUtil,times(1)).readAllContentByLines();

    }

    @Test
    void testWriteTermToFile() throws IOException {

        String term = "New Term";
        fileUtil.writeTermToFile(term);

        
        Mockito.verify(fileUtil).writeTermToFile(term);
        Mockito.verify(fileUtil,times(1)).writeTermToFile(term);
    }
}
