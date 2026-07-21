package org.texttechnologylab.anon.controllers;

import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.texttechnologylab.anon.config.DUUIProperties;
import org.texttechnologylab.anon.config.InputProperties;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;
import org.texttechnologylab.anon.data.AudioInput;
import org.texttechnologylab.anon.data.ImageInput;
import org.texttechnologylab.anon.data.Input;
import org.texttechnologylab.anon.data.TextInput;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
class InputController {
    @Autowired
    private InputProperties inputProperties;

    @Autowired
    private DUUIProperties duuiProperties;

// get - read data


    @GetMapping("/api/modalities")
    public List<ApplicationEnums.MODALITIES> getAvailableModalities(){
        return inputProperties.getModalities();
    }


    /**
     * Lists available methods (such as "remove"/"blackout" depending on the inputType)
     * GET /methods?inputType=text
     * @param inputType
     * @return
     */
    @GetMapping("/api/methods")
    public List<String> getAvailableMethods(@RequestParam String inputType){
        ApplicationEnums.MODALITIES modality = ApplicationEnums.MODALITIES.valueOf(inputType);
        switch (modality){
            case TEXT:
                return duuiProperties.getTextAnonTypes();
            case IMAGE:
                return duuiProperties.getImageAnonTypes();
            case AUDIO:
                return duuiProperties.getAudioAnonTypes();
            default:
                return List.of("Nothing found for the given input Type");
        }
    }









    public ResponseEntity<String> submitImageInput(){
        return null;
    }
    public ResponseEntity<String> submitAudioInput(){
        return null;
    }


}
