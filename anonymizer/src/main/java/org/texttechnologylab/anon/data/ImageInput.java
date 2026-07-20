package org.texttechnologylab.anon.data;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import java.util.List;

public class ImageInput extends Input{

    public ImageInput() {
        super("image");
    }
    @Override
    JCas toJCas(JCas cas) throws ResourceInitializationException, CASException {
        return null;
    }
}
