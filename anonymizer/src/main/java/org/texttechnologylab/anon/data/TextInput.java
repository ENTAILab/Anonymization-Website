package org.texttechnologylab.anon.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;

public class TextInput implements Input{

    private Long id;
    private String input_type;
    // todo - show only methods for selected Input_type
    private List<String> anon_method = new ArrayList<>();
    
    private String input_text;
    private Boolean dif_views = false; // weather for each anon method a new should be set, if false use initialView


    TextInput(  ){

    }

    TextInput(String input_type){
        this.input_type=input_type;
    }

    @Override
    public String getType(){
        return this.input_type;
    }


    public void setType(String type ){
        this.input_type = type;
    }

    @Override
    public List<String> getMethods() {
        return this.anon_method;
    }

    public void addMethod(String method ){
        this.anon_method.add(method);
    }

    @Override
    public Boolean getDifViews() {
        return null;
    }

    @Override
    public void setDifViews(Boolean difViews) {
        this.dif_views=difViews;
    }

    public void setText(String text ){
        this.input_text = text;
    }

    /**
     * same cas is passed to the classes to be filled, as
     * @param cas
     * @return
     * @throws ResourceInitializationException
     * @throws CASException
     */
    public JCas toJCas(JCas cas) throws ResourceInitializationException, CASException {
        cas.reset();
        cas.setDocumentText(this.input_text);

        return cas;
    }

}
