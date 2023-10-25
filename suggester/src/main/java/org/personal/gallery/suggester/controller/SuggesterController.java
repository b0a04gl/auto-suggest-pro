package org.personal.gallery.suggester.controller;

import org.personal.gallery.suggester.service.ports.SuggesterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/suggester")
@CrossOrigin(origins = "http://localhost:63342")
public class SuggesterController {
    private final SuggesterService suggesterService;

    private final Logger logger = LoggerFactory.getLogger(SuggesterController.class);
    @Autowired
    public SuggesterController(SuggesterService suggesterService) {
        this.suggesterService = suggesterService;
    }

    @GetMapping("/suggestWords")
    public ResponseEntity<List<String>> suggestWords(@RequestParam String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            logger.error("Invalid request");
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }
        logger.info("API hit for /suggestWords/userInput={}",userInput);
        List<String> suggestions = suggesterService.getSuggestions(userInput);

        return ResponseEntity.ok(suggestions);
    }
}
