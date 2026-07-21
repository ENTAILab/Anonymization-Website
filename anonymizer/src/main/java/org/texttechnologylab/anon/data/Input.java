package org.texttechnologylab.anon.data;

import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Input {
    protected Long id;
    protected String input_type;
    protected List<String> anon_method = new ArrayList<>();
    protected Boolean dif_views = false;



    protected Input(String type){
        this.input_type=type;
    }
    public String getType() {
        return this.input_type;
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


    abstract JCas toJCas(JCas cas) throws ResourceInitializationException, CASException;


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
