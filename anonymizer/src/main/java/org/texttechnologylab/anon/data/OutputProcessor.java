package org.texttechnologylab.anon.data;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.texttechnologylab.anon.config.DUUIProperties;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;
import org.texttechnologylab.anon.exceptions.AnnotationError;
import org.texttechnologylab.anon.helper.Helper;

import java.util.List;

public class OutputProcessor {
    List<ApplicationEnums.MODALITIES> modalities;




    public OutputProcessor(List<ApplicationEnums.MODALITIES> modalities) {
        this.modalities = modalities;
    }

    public Helper.ProcessResponse processJCas(JCas cas) throws CASException {
        if (modalities.isEmpty()) {
            throw new AnnotationError("Something went wrong processing JCas");
        } else if (modalities.contains(ApplicationEnums.MODALITIES.TEXT)) {
            return processText(cas);
        }
        return new Helper.ProcessResponse("Error Processing JCas");
    }

    private Helper.ProcessResponse processText(JCas cas) throws CASException {
        String textView = DUUIProperties.getInstance().getTextView();
        return new Helper.ProcessResponse(cas.getView(textView).getDocumentText());
    }
}
