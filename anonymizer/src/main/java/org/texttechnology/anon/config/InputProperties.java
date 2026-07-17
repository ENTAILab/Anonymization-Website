package org.texttechnology.anon.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "website")
public class WebsiteProperties {
    private List<String> modalities;
    private List<String> textAnonTypes;
    private List<String> imageAnonTypes;
    private List<String> audioAnonTypes;

}
