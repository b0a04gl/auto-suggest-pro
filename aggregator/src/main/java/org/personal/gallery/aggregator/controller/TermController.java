package org.personal.gallery.aggregator.controller;

import org.personal.gallery.aggregator.service.ports.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/terms")
public class TermController {
    private final Logger logger = LoggerFactory.getLogger(TermController.class);

    private final FileUtil fileUtil;

    @Autowired
    public TermController(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @PostMapping("/collect")
    public ResponseEntity<String> collectTerm(@RequestParam String term) {
        if (term == null || term.trim().isEmpty()) {
            return new ResponseEntity<>("Term cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        logger.info("API hit /collect?term={}", term);
        fileUtil.writeTermToFile(term + " ");
        return ResponseEntity.ok("Received term: " + term);
    }
}
