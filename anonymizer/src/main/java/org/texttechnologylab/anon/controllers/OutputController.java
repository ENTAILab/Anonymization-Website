package org.texttechnologylab.anon.controllers;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.texttechnologylab.anon.config.InputProperties;
import org.texttechnologylab.anon.data.Output;
import org.texttechnologylab.anon.duui.DUUIInteractions;

@RestController
@RequestMapping
public class OutputController {
    @Autowired
    private InputProperties inputProperties;
    @Autowired
    private Output output;
    @Autowired
    private DUUIInteractions dUUIInteractions;

    /**
     * takes the textResult, turns it into an object that ca be jsonified
     * @return
     */
    @GetMapping("/api/results")
    public ResponseEntity<String> getResult() {
        String text = output.getOutputText();
        if (text == null) {
            text = "";
        }
        return ResponseEntity.ok(text);
    }



}
