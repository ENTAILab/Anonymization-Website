package org.texttechnologylab.anon.controllers;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.texttechnologylab.anon.config.InputProperties;
import org.texttechnologylab.anon.data.Output;

@RestController
@RequestMapping
public class OutputController {
    @Autowired
    private InputProperties inputProperties;
    @Autowired
    private Output output;

    /**
     * takes the textResult, turns it into an object that ca be jsonified
     * @param textResult
     * @return
     */
    @PutMapping("/api/results")
    public ResponseEntity<?> updateResult(@RequestBody String textResult) {
        output.setOutputText(textResult);
        return ResponseEntity.ok().build();
    }


}
