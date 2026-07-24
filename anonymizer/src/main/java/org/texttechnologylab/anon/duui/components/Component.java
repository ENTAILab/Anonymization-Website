package org.texttechnologylab.anon.duui.components;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.uima.util.InvalidXMLException;
import org.texttechnologylab.DockerUnifiedUIMAInterface.DUUIComposer;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIPipelineComponent;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIRemoteDriver;
import org.texttechnologylab.anon.config.DUUIProperties;
import org.texttechnologylab.anon.config.InputProperties;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;
import org.texttechnologylab.anon.exceptions.MissingUriException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.HashMap;

public abstract class Component {
    protected final DUUIProperties duuiProperties = new DUUIProperties();
    protected final InputProperties inputProperties = new InputProperties();

    // docker uri has higher priority over the remote one. The remote one will be used as fallback
    protected String uri = null;
    protected ApplicationEnums.MODALITIES modality;
    protected DUUIRemoteDriver.Component remoteComponent;

    protected Map<String, String> parameters = new HashMap<>();


    public String viewName;

    Component(String type) throws URISyntaxException, IOException {
        this.modality = ApplicationEnums.MODALITIES.valueOf(type);
        this.viewName = DUUIProperties.getInstance().getView(modality);
    }

    public String getViewName() {
        return this.viewName;
    }

    protected void checkUri(){
        if(uri == null || uri.isEmpty()){
            throw new MissingUriException("Please provide a URI");
        }
    }
    public void setUri(String uri) throws URISyntaxException, IOException {
        System.out.println("setting URI: " + uri);
        this.uri = uri;
        checkUri();
        createComponent();
    }

    public String getUri() {
        return this.uri;
    }

    protected void createComponent() throws URISyntaxException, IOException {
        this.remoteComponent = new DUUIRemoteDriver.Component(this.uri);
    }

    public void addProperties(Map<String,String> properties) throws IOException {
        for  (String key : properties.keySet()) {
            System.out.println("key: " + key + " value: " + properties.get(key));
            addProperty(key, properties.get(key));
        }
    }
    public void addProperty(String key, String value) throws IOException {
        System.out.println("key: " + key + " value: " + value);

        this.remoteComponent.withParameter(key, value);
        this.parameters.put(key, value);
    }

    public void addSourceView(String viewName){
        this.remoteComponent.withSourceView(viewName);
    }
    public void addTargetView(String viewName){
        this.remoteComponent.withTargetView(viewName);
        System.out.println("Adding target view: " + viewName);

    }


    public abstract void removePropertiesFromComponent();
    public abstract void addToComposer(DUUIComposer composer) throws CompressorException, InvalidXMLException, IOException, SAXException;

    public DUUIPipelineComponent getRemoteComponent() {
        return remoteComponent.build().withTimeout(1000);
    }

    // TODO Adjust modal
    public static Component newComponent(ApplicationEnums.MODALITIES modality) throws URISyntaxException, IOException {
        switch (modality) {
            case TEXT -> {
                return new TextComponent();
            }
            case AUDIO ->  {
                return null;
            }
            case IMAGE -> {
                return null;
            }
            default -> {
                return null;
            }
        }
    }
    @Override
    public String toString() {
    return "Component{" +
            "uri='" + uri + '\'' +
            ", modality=" + modality +
            ", viewName='" + viewName + '\'' +
            ", parameters=" + parameters +
            '}';
    }
}
