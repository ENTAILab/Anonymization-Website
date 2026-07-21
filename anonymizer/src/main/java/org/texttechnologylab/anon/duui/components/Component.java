package org.texttechnologylab.anon.duui.components;

import org.apache.uima.UIMAException;
import org.springframework.beans.factory.annotation.Autowired;
import org.texttechnologylab.DockerUnifiedUIMAInterface.DUUIComposer;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIDockerDriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIPipelineComponent;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIRemoteDriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.IDUUIDriverInterface;
import org.texttechnologylab.anon.config.DUUIProperties;
import org.texttechnologylab.anon.config.InputProperties;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;
import org.texttechnologylab.anon.data.Input;
import org.xml.sax.SAXException;

import java.io.IOException;

public abstract class Component {
    protected final DUUIProperties duuiProperties = new DUUIProperties();
    protected final InputProperties inputProperties = new InputProperties();

    // docker uri has higher priority over the remote one. The remote one will be used as fallback
    private static String remoteUri = null;
    private static String dockerUri = null;
    private ApplicationEnums.MODALITIES modality;
    private ApplicationEnums.DUUICOMPONENTS componentType;
    private IDUUIDriverInterface component;

    Component(String type){
        this.modality = ApplicationEnums.MODALITIES.valueOf(type);

    }

    public void setDockerUri(String dockerUri) {
        this.dockerUri = dockerUri;
    }

    public void setRemoteUri(String remoteUri) {
        this.remoteUri = remoteUri;
    }

    public void setComponent() throws IOException, UIMAException, SAXException {
        if (dockerUri == null){
            component = new DUUIDockerDriver();
        }else{
            component = new DUUIRemoteDriver();
        }
    }

    public abstract void addToComposer(DUUIComposer composer);

}
