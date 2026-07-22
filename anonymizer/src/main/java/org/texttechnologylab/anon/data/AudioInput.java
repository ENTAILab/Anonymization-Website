package org.texttechnologylab.anon.data;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;

import java.util.List;

public class AudioInput extends Input{
    public AudioInput() {
        super(ApplicationEnums.MODALITIES.AUDIO);
    }
    @Override
    public JCas toJCas(JCas cas) throws ResourceInitializationException, CASException {
        return null;
    }
}
