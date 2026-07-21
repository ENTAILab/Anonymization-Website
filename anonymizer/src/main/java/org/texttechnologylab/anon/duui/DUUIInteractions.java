package org.texttechnologylab.anon.duui;

import org.apache.uima.UIMAException;
import org.texttechnologylab.DockerUnifiedUIMAInterface.DUUIComposer;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIDockerDriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIRemoteDriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.IDUUIDriverInterface;
import org.texttechnologylab.DockerUnifiedUIMAInterface.lua.DUUILuaContext;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;
import org.texttechnologylab.anon.data.Input;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;

public class DUUIInteractions {

    // todo make this like singleton sth sth

    private DUUIComposer composer;

    public DUUIInteractions() throws IOException, URISyntaxException, UIMAException, SAXException {
        initialize();
    }

    private void initialize() throws IOException, URISyntaxException, UIMAException, SAXException {
        DUUILuaContext luaContext = new DUUILuaContext().withJsonLibrary();
        this.composer = new DUUIComposer()
                .withSkipVerification(true)
                .withLuaContext(luaContext)
                .withWorkers(1);

        DUUIRemoteDriver remoteDriver = new DUUIRemoteDriver();
        DUUIDockerDriver dockerDriver = new DUUIDockerDriver();
        this.composer.addDriver(remoteDriver, dockerDriver);


    }

    private void addComponent(String modality, String uri, ApplicationEnums.DUUICOMPONENTS componentType){

    }
    // idea: a DUUI class with an instance per user ? TODO how the hell does one implement that
    // idea2: a set number of Instances and use a queue system?



    // gets the cas passed from the input and runs it through the pipeline depending on the other parameters passed

    // gets output and passes it back to the Output Controller

    // implement Output properties for JSON / CAS / Plain Text etc


    //___________________________

    // Text options

    /*
     TODO duui remote driver or docker driver?
     - enter form on website that lets u choose between local / docker => provide link, or given remote
     - add components according to that, add just DockerDiver and RemoteDriver to every pipeline so anything can be mixed and matched
     -   
     */


    public void submit(Input input){

    }

}
