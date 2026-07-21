package org.texttechnologylab.anon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "duui")
public class DUUIProperties {
    private String endpoint;
    private long timeout;
    private List<ApplicationEnums.DUUICOMPONENTS> components;

    List<String> textAnonTypes;
    List<String> imageAnonTypes;
    List<String> audioAnonTypes;
    List<String> textAnonProperties;
    List<String> imageAnonProperties;
    List<String> audioAnonProperties;

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

    public List<String> getTextAnonProperties() {
        return textAnonProperties;
    }

    public void setTextAnonProperties(List<String> textAnonProperties) {
        this.textAnonProperties = textAnonProperties;
    }

    public List<String> getImageAnonProperties() {
        return imageAnonProperties;
    }

    public void setImageAnonProperties(List<String> imageAnonProperties) {
        this.imageAnonProperties = imageAnonProperties;
    }

    public List<String> getAudioAnonProperties() {
        return audioAnonProperties;
    }

    public void setAudioAnonProperties(List<String> audioAnonProperties) {
        this.audioAnonProperties = audioAnonProperties;
    }

    public void setComponents(List<ApplicationEnums.DUUICOMPONENTS> components){
        this.components = components;
    }

    public List<ApplicationEnums.DUUICOMPONENTS> getComponents() {
        return components;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
