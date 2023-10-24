package org.personal.gallery.aggregator.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.gallery.aggregator.service.ports.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TermControllerTest {

    @Mock
    private FileUtil fileUtil;

    @InjectMocks
    private TermController termController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(termController).build();
    }

    @Test
     void testCollectTermSuccess() throws Exception {
        String term = "TestTerm";

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/terms/collect")
                        .param("term", term)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Received term: " + term, response.getContentAsString());
        Mockito.verify(fileUtil).writeTermToFile(term + " ");
    }

    @Test
    void testCollectTermNull() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/terms/collect")
                        .param("term", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }


}

