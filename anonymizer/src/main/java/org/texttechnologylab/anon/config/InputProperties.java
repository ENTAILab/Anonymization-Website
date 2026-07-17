package org.texttechnologylab.anon.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "input")
public class InputProperties {
    private List<String> modalities;
    private List<String> textAnonTypes;
    private List<String> imageAnonTypes;
    private List<String> audioAnonTypes;

    public List<String> getModalities() {
        return modalities;
    }

    public void setModalities(List<String> modalities) {
        this.modalities = modalities;
    }

    public List<String> getTextAnonTypes() {
        return textAnonTypes;
    }

    public void setTextAnonTypes(List<String> textAnonTypes) {
        this.textAnonTypes = textAnonTypes;
    }

    public List<String> getImageAnonTypes() {
        return imageAnonTypes;
    }

    public void setImageAnonTypes(List<String> imageAnonTypes) {
        this.imageAnonTypes = imageAnonTypes;
    }

    public List<String> getAudioAnonTypes() {
        return audioAnonTypes;
    }

    public void setAudioAnonTypes(List<String> audioAnonTypes) {
        this.audioAnonTypes = audioAnonTypes;
    }

}
