package org.texttechnologylab.anon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.texttechnologylab.anon.config.InputProperties;
import org.texttechnologylab.anon.data.TextInput;

import java.util.List;

@RestController
@RequestMapping
class InputController {
    @Autowired
    private InputProperties inputProperties;
// get - read data


    @GetMapping("/api/modalities")
    public List<String> getAvailableModalities(){
        return inputProperties.getModalities();
    }
    // TODO lowk dont remember which methods there were....
    /**
     * Lists available methods (such as "remove"/"blackout" depending on the inputType)
     * GET /methods?inputType=text
     * @param inputType
     * @return
     */
    @GetMapping("/api/methods")
    public List<String> getAvailableMethods(@RequestParam String inputType){
        switch (inputType){
            case "text":
                return inputProperties.getTextAnonTypes();
            case "image":
                return inputProperties.getImageAnonTypes();
            case "audio":
                return inputProperties.getAudioAnonTypes();
            default:
                return List.of("Nothing found for the given input Type");
        }
    }

    public ResponseEntity<String> submitInput(@RequestBody TextInput input){
        // TODO input
        return null;
    }
}
