package org.texttechnologylab.anon.data;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.texttechnologylab.anon.config.DUUIProperties;
import org.texttechnologylab.anon.config.InputProperties;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Input {

    protected Long id;
    protected ApplicationEnums.MODALITIES modality;
    protected List<String> anon_method = new ArrayList<>();
    protected Boolean dif_views = false;
    public String viewName;

    public Input(ApplicationEnums.MODALITIES modality) {
        this.modality = modality;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }




    public List<String> getMethods() {
        return this.anon_method;
    }
    public void setAnon_methods(List<String> methods){
        this.anon_method = methods;
    }
    public void addMethod(String method) {
        this.anon_method.add(method);
    }

    public Boolean getDifViews() {
        return this.dif_views;
    }

    public void setDifViews(Boolean difViews) {
        this.dif_views = difViews;
    }


    public abstract JCas toJCas(JCas cas) throws ResourceInitializationException, CASException;


    public Map<String, Object> toMap() {
        Map<String, Object> fields = new LinkedHashMap<>();
        Class<?> clazz = this.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    fields.put(field.getName(), field.get(this));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + toMap();
    }

    public static Input createInput(ApplicationEnums.MODALITIES modality){
        return switch (modality){
            case TEXT -> new TextInput();
            case AUDIO -> new AudioInput();
            case IMAGE -> new ImageInput();
        };
    }

}
