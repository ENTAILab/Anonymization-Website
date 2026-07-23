package org.texttechnologylab.anon.controllers;

import org.apache.uima.UIMAException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.texttechnologylab.anon.config.DUUIProperties;
import org.texttechnologylab.anon.config.InputProperties;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;
import org.texttechnologylab.anon.data.Input;
import org.texttechnologylab.anon.data.Output;
import org.texttechnologylab.anon.data.TextInput;
import org.texttechnologylab.anon.duui.DUUIInteractions;
import org.texttechnologylab.anon.helper.Helper;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;


@RestController
public class MainController {
    @Autowired
    private InputProperties inputProperties;

    @Autowired
    private DUUIProperties duuiProperties;
    @Autowired
    private Output output;

    private final PipelineService pipelineService;

    MainController(PipelineService pipelineService) throws IOException, URISyntaxException, UIMAException, SAXException {
        this.pipelineService = pipelineService;

    }
    /**
     * clean up all the backend stuff so its a clean slate for next call
     * (so far just the cas but yknow multiple users TODO)
     * @return
     */
    @PostMapping("/api/results/clean")
    public ResponseEntity<?> clearPipeline(){

        // TODO adjust, this only calls if sth changed between requests ASIDE from text
        // actually maybe this can be implemented with the fact that the class attributes are NOT cleared between calls
        // give it maybe a check?
        pipelineService.reset();
        return ResponseEntity.ok().build();
    }




    @PostMapping("/api/process")
    public ResponseEntity<?> process(
            @RequestBody Helper.ProcessRequest request) throws Exception {
        //  dpeending on modality => create new modalityInput
        // if len(modalities) > 1 => enforce dif views
        // get cas of that input type  (each dif view if multiple input types, else depending on parameter)
        // send TextInput to DUUIInteractions => runs pipeline => returns annotated cas => output controller

        Helper.checkIntegrity(request);
        Helper.stripParameters(request);
        System.out.println("Processing Request...");
        // todo extend for the other modalities  with a loop
        // 1. make input
        ApplicationEnums.MODALITIES modality = ApplicationEnums.MODALITIES.valueOf(request.modality().toUpperCase());

        Input input = Input.createInput(modality);
        input.setDifViews(request.views());
        input.setAnon_methods(request.methods());

        if (input instanceof TextInput textInput) {
           textInput.setInput_text(request.textinput());
        }
        pipelineService.addInput(input);

        // 2. add component according to parameters and the provided link / if remote or no



        /* TODO get the Properties from the site
        - to CAS implementation
        - DUUI interactins add the components and then run sth sth

        - return back to website using output controlelr
        -
         */
        pipelineService.initializePipeline(request);
        // return ok if type created
        return ResponseEntity.ok(input);
        }


}


