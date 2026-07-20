package org.texttechnologylab.anon.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;
import org.bson.json.JsonObject;

public class TextInput extends Input {
    private String input_text;

    public TextInput() {
       super("text");
    }

    public void setInput_text(String text) {
        this.input_text = text;
    }


    /**
     *
     * @param cas
     * @return
     * @throws ResourceInitializationException
     * @throws CASException
     */
    @Override
    public JCas toJCas(JCas cas) throws ResourceInitializationException, CASException {
        cas.reset();
        if(this.dif_views){
            cas.createView("textView");
        }
        cas.setDocumentText(this.input_text);
        return cas;
    }

}
