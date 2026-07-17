package org.texttechnologylab.anon.data;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Input {
    String getType();
    void setType(String type);

    List<String> getMethods();
    void addMethod(String method);

    Boolean getDifViews();
    void setDifViews(Boolean difViews);

    JCas toJCas(JCas cas) throws ResourceInitializationException, CASException;


}
