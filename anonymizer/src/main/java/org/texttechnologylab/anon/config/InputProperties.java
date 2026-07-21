package org.texttechnologylab.anon.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "input")
public class InputProperties {
    private List<ApplicationEnums.MODALITIES> modalities;


    public List<ApplicationEnums.MODALITIES> getModalities() {
        return modalities;
    }

    public void setModalities(List<ApplicationEnums.MODALITIES> modalities) {
        this.modalities = modalities;
    }



}
