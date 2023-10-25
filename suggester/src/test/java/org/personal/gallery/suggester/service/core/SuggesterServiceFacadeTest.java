package org.personal.gallery.suggester.service.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.gallery.suggester.service.core.SuggesterServiceFacade;
import org.personal.gallery.suggester.service.core.model.WordFrequency;
import org.personal.gallery.suggester.service.ports.Redis;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class SuggesterServiceFacadeTest {

    @Mock
    private Redis redisClient;

    @InjectMocks
    private SuggesterServiceFacade suggesterServiceFacade;

    @BeforeEach
    public void setUp() {
        suggesterServiceFacade = new SuggesterServiceFacade(redisClient);
    }

    @Test
    void testGetSuggestions() {
        
        String jsonResponse = "[{\"word\":\"apple\",\"frequency\":3}," +
                "{\"word\":\"application\",\"frequency\":2}]";

        
        Mockito.when(redisClient.retrieve(anyString())).thenReturn(jsonResponse);

        
        List<String> suggestions = suggesterServiceFacade.getSuggestions("app");

        
        Mockito.verify(redisClient).retrieve("app");

        
        assertEquals(2, suggestions.size());
        assertEquals("apple", suggestions.get(0));
        assertEquals("application", suggestions.get(1));
    }
}

