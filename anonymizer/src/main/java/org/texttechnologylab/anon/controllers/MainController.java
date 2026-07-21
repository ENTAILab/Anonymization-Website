package org.texttechnologylab.anon.controllers;

import org.apache.uima.UIMAException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;
import org.texttechnologylab.anon.data.Input;
import org.texttechnologylab.anon.data.TextInput;
import org.texttechnologylab.anon.duui.DUUIInteractions;
import org.texttechnologylab.anon.exceptions.MissingAnonTypeException;
import org.texttechnologylab.anon.exceptions.MissingModalityException;
import org.texttechnologylab.anon.exceptions.MissingUriException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;


@Controller
public class MainController {
    private static DUUIInteractions duuiInteractions;

    MainController() throws IOException, URISyntaxException, UIMAException, SAXException {
        duuiInteractions = new DUUIInteractions();
    }

    public record ProcessRequest(
            String modality,
            String textinput,
            String fileinput,
            Boolean views,
            List<String> methods,
            Map<String, String> parameters,
            String duuicomponent,
            String duuiurl
    ){}

    /**
     * simple checker to make sure no empty values are passed
     * @param request
     */
    private void checkIntegrity(ProcessRequest request){
        if(request.modality == null || request.modality.isBlank() ){
            throw new MissingModalityException("Please select a Modality!");
        }
        if(request.methods.isEmpty()){
            throw new MissingAnonTypeException("Please select at least one Anonymization Type");
        }
        if(request.duuiurl == null || request.duuiurl.isBlank()){
            throw new MissingUriException("No remote or docker URI passed, please provide one!");
        }
    }



    @PostMapping("/api/process")
    public ResponseEntity<?> process(
            @RequestBody ProcessRequest request){
        //  dpeending on modality => create new modalityInput
        // if len(modalities) > 1 => enforce dif views
        // get cas of that input type  (each dif view if multiple input types, else depending on parameter)
        // send TextInput to DUUIInteractions => runs pipeline => returns annotated cas => output controller

        checkIntegrity(request);
        // todo extend for the other modalities  with a loop
        // 1. make input
        ApplicationEnums.MODALITIES modality = ApplicationEnums.MODALITIES.valueOf(request.modality.toUpperCase());

        Input input = Input.createInput(modality);
        input.setDifViews(request.views);
        input.setAnon_methods(request.methods);

        if (input instanceof TextInput textInput) {
           textInput.setInput_text(request.textinput);
        }

        System.out.println(input.toString());

        // 2. add component according to parameters and the provided link / if remote or no

        // return ok if type created
        return ResponseEntity.ok(input);
        }


}


