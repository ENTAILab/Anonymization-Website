package org.texttechnologylab.anon.controllers;

import org.apache.uima.UIMAException;
import org.bytedeco.libfreenect._freenect_context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.texttechnologylab.anon.config.DUUIProperties;
import org.texttechnologylab.anon.config.InputProperties;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;
import org.texttechnologylab.anon.data.Input;
import org.texttechnologylab.anon.data.TextInput;
import org.texttechnologylab.anon.duui.DUUIInteractions;
import org.texttechnologylab.anon.duui.components.Component;
import org.texttechnologylab.anon.helper.InputHelper;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.texttechnologylab.anon.duui.components.Component.newComponent;


@Controller
public class MainController {
    @Autowired
    private InputProperties inputProperties;

    @Autowired
    private DUUIProperties duuiProperties;

    private final DUUIInteractions dUUIInteractions;
    private List<Input> inputs;

    MainController(DUUIInteractions dUUIInteractions) throws IOException, URISyntaxException, UIMAException, SAXException {
        this.dUUIInteractions = dUUIInteractions;
        this.inputs = new ArrayList<>();

    }


    private void initializePipeline(InputHelper.ProcessRequest request ) throws Exception {
        System.out.println("Initializing Pipeline...");
        ApplicationEnums.MODALITIES modality = ApplicationEnums.MODALITIES.valueOf(request.modality().toUpperCase());
        for(Input input : inputs) {
            input.toJCas(dUUIInteractions.getJcas());
        }

        List<Component> components = new ArrayList<>();
        boolean first = true;
        for(String method : request.methods()) {
            Component component = newComponent(modality);
            if (component == null) {
                System.out.println("component is null");
            }



            // Todo how to do multiple URIS for dif modalities
            // sets uri and "creates" the component (as uri is needed to create it)
            component.setUri(request.duuiurl());

            if (inputs.getFirst().getDifViews()) {
                component.addTargetView(component.getViewName());
                // no source -> always reads initial view
            } else if (inputs.size() > 1) {
                component.addTargetView(component.getViewName());
                if (!first) {
                    component.addSourceView(component.getViewName());
                }
                // first component: no source -> reads initial view; writes to shared view
            }
            component.addProperties(request.parameters());
            // todo how to make it possible that the mode selection is always a "many select"
            component.addProperty("mode", method);

            System.out.println(component);
            components.add(component);
            first = false;
        }



        dUUIInteractions.getJcas().getViewIterator().forEachRemaining(viewIterator -> {
            System.out.println("ViewIterator: " + viewIterator.getViewName());
        });

        dUUIInteractions.addComponents(components);
        dUUIInteractions.addXMIWriter();

        dUUIInteractions.sanityCheck();


        //dUUIInteractions.addComponent(component);
        dUUIInteractions.run();

    }



    @PostMapping("/api/process")
    public ResponseEntity<?> process(
            @RequestBody InputHelper.ProcessRequest request) throws Exception {
        //  dpeending on modality => create new modalityInput
        // if len(modalities) > 1 => enforce dif views
        // get cas of that input type  (each dif view if multiple input types, else depending on parameter)
        // send TextInput to DUUIInteractions => runs pipeline => returns annotated cas => output controller

        InputHelper.checkIntegrity(request);
        InputHelper.stripParameters(request);
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
        this.inputs.add(input);

        // 2. add component according to parameters and the provided link / if remote or no



        /* TODO get the Properties from the site
        - to CAS implementation
        - DUUI interactins add the components and then run sth sth

        - return back to website using output controlelr
        -
         */
        initializePipeline(request);
        // return ok if type created
        return ResponseEntity.ok(input);
        }


}


