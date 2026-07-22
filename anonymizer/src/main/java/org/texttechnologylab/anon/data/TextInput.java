package org.texttechnologylab.anon.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;

import org.apache.uima.resource.ResourceInitializationException;
import org.bson.json.JsonObject;
import org.texttechnologylab.anon.config.DUUIProperties;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;

public class TextInput extends Input {
    private String input_text;

    public TextInput() {
       super(ApplicationEnums.MODALITIES.TEXT);
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
        cas.getView("_InitialView").setDocumentText(this.input_text);
        cas.setDocumentLanguage("en");

        return cas;
    }

}
