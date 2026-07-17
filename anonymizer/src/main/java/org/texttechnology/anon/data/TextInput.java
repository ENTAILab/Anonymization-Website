package org.texttechnology.anon.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.jcas.JCas;

import jakarta.persistence.Entity;
@Entity
class Input {

    private String input_type;
    // todo - show only methods for selected Input_type
    private List<String> anon_method = new ArrayList<>();
    
    private String input_text;


    Input(  ){

    }

    Input(String input_type){
        this.input_type=input_type;
    }

    public String getType(){
        return this.input_type;
    }
    public List<String> getMethod(){
        return this.anon_method;
    }
    public String getContent(){
        return this.input_type;
    }

    public void setType(String type ){
        this.input_type = type;
    }
    public void addMethod(String method ){
        this.anon_method.add(method);
    }
      public void setText(String text ){
        this.input_text = text;
    }


    public JCas toJCas(){
        JCas cas = JCasFactory.
    }

}
