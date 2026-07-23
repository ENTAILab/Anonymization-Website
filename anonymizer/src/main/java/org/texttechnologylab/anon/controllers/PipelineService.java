package org.texttechnologylab.anon.controllers;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;
import org.texttechnologylab.anon.data.Input;
import org.texttechnologylab.anon.data.Output;
import org.texttechnologylab.anon.data.OutputProcessor;
import org.texttechnologylab.anon.duui.DUUIInteractions;
import org.texttechnologylab.anon.duui.components.Component;
import org.texttechnologylab.anon.helper.Helper;

import java.util.ArrayList;
import java.util.List;

import static org.texttechnologylab.anon.duui.components.Component.newComponent;

@Service
public class PipelineService {
    @Autowired
    private Output output;

    private final DUUIInteractions dUUIInteractions;
    private final List<Input> inputs = new ArrayList<>();
    private final List<ApplicationEnums.MODALITIES> modalities = new ArrayList<>();



    public PipelineService(DUUIInteractions dUUIInteractions){
        this.dUUIInteractions = dUUIInteractions;
    }

    /**
     * the whole happening => gets request, uses input list to make the components
     * adds components, runs the composer and sents result to be processed
     * @param request
     * @throws Exception
     */
    public void initializePipeline(Helper.ProcessRequest request ) throws Exception {
        System.out.println("Initializing Pipeline...");
        output.setOutputText(null);
        ApplicationEnums.MODALITIES modality = ApplicationEnums.MODALITIES.valueOf(request.modality().toUpperCase());
        this.modalities.add(modality);
        if (this.inputs.size() > 1) {
            System.out.println("STH WENT WRONG");
        }
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

            component.addTargetView(component.getViewName());
            /*
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
            */

            component.addProperties(request.parameters());
            // todo how to make it possible that the mode selection is always a "many select"
            component.addProperty("mode", method);

            System.out.println(component);
            components.add(component);
            first = false;
        }


        for (ApplicationEnums.MODALITIES mod : modalities) {
            dUUIInteractions.addView(mod);
        }
        try {

            // adding the actual components:
            dUUIInteractions.addComponents(components);
            // test mode (as the website reads out the Sofa String on that specific view)
            //dUUIInteractions.getJcas().getView("textView").setDocumentText("This is a placeholder text for simulating the output!");



            dUUIInteractions.addXMIWriter();

            //dUUIInteractions.sanityCheck();



            dUUIInteractions.run();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        //Helper.simpleTempExporter(dUUIInteractions.getJcas(), "test1");

        OutputProcessor outputProcessor = new OutputProcessor(modalities);
        Helper.ProcessResponse result = outputProcessor.processJCas(dUUIInteractions.getJcas());
        output.setOutputText(result.text());


    }

    /**
     * resets everything (input, modalities, CAS and the composer)
     */
    public void reset(){
        dUUIInteractions.resetCAS();
        dUUIInteractions.resetComposer();
        inputs.clear();
        modalities.clear();
    }
    public List<Input> getInputs() { return inputs; }

    public void addInput(Input input){this.inputs.add(input);}


}
