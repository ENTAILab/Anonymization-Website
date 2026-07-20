package org.texttechnologylab.anon.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.texttechnologylab.anon.data.Input;
import org.texttechnologylab.anon.data.TextInput;

import java.util.List;
import java.util.Map;


@Controller
public class MainController {

    public record ProcessRequest(
            String modality,
            String textinput,
            String fileinput,
            Boolean views,
            List<String> methods,
            Map<String, String> parameters
    ){}




    @PostMapping("/api/process")
    public ResponseEntity<?> process(
            @RequestBody ProcessRequest request){
        //  dpeending on modality => create new modalityInput
        // if len(modalities) > 1 => enforce dif views
        // get cas of that input type  (each dif view if multiple input types, else depending on parameter)
        // send TextInput to DUUIInteractions => runs pipeline => returns annotated cas => output controller

        Input.Modality modality = Input.Modality.valueOf(request.modality.toUpperCase());
        Input input = Input.createInput(modality);
        input.setDifViews(request.views);
        input.setAnon_methods(request.methods);
        if (input instanceof TextInput) {
            ((TextInput) input).setInput_text(request.textinput);
        }

        System.out.println(input.toString());


        // return ok if type created
        return ResponseEntity.ok(input);
    }

}
