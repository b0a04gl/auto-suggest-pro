package org.personal.gallery.suggester.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.gallery.suggester.service.ports.SuggesterService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SuggesterControllerTest {

    @Mock
    private SuggesterService suggesterService;

    @InjectMocks
    private SuggesterController suggesterController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(suggesterController).build();
    }

    @Test
    void testSuggestWordsWithSuggestions() throws Exception {
        String userInput = "ap";
        List<String> mockSuggestions = Arrays.asList("apple", "application", "appeal");

        when(suggesterService.getSuggestions(userInput)).thenReturn(mockSuggestions);

        mockMvc.perform(MockMvcRequestBuilders.get("/suggester/suggestWords")
                        .param("userInput", userInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("apple"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value("application"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]").value("appeal"));
    }

    @Test
    void testSuggestWordsWithEmptyInput() throws Exception {
        String userInput = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/suggester/suggestWords")
                        .param("userInput", userInput)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
}
