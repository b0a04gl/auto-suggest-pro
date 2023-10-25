package org.personal.gallery.suggester.controller;

import org.personal.gallery.suggester.service.ports.SuggesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/suggester")
public class SuggesterController {
    private final SuggesterService suggesterService;

    @Autowired
    public SuggesterController(SuggesterService suggesterService) {
        this.suggesterService = suggesterService;
    }

    @GetMapping("/suggestWords")
    public ResponseEntity<List<String>> suggestWords(@RequestParam String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }

        List<String> suggestions = suggesterService.getSuggestions(userInput);

        return ResponseEntity.ok(suggestions);
    }
}
