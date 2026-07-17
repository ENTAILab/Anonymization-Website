package org.texttechnologylab.anon.config;

import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasPool;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cas")
public class CASPoolConfig {
    private int casPoolSize;
    private String typesystemPath;

    private static TypeSystemDescription tsd = null;

    private TypeSystemDescription getTypeSystem(){
        if (tsd == null){
            tsd =  TypeSystemDescriptionFactory.createTypeSystemDescriptionFromPath(typesystemPath);
        }
        return tsd;
    }


    // TODO figure out how to do multiple CAS at once
    @Bean
    public CasPool casPool() {
        return null;
    }
}
