package org.texttechnologylab.anon.duui;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.uima.UIMAException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.json.JsonCasSerializer;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.springframework.stereotype.Service;
import org.texttechnologylab.DockerUnifiedUIMAInterface.DUUIComposer;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIDockerDriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIRemoteDriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIUIMADriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.IDUUIDriverInterface;
import org.texttechnologylab.DockerUnifiedUIMAInterface.lua.DUUILuaContext;
import org.texttechnologylab.anon.config.DUUIProperties;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;
import org.texttechnologylab.anon.data.Input;
import org.texttechnologylab.anon.duui.components.Component;
import org.xml.sax.SAXException;
import org.dkpro.core.io.xmi.XmiWriter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

@Service
public class DUUIInteractions {

    // todo make this like singleton sth sth

    private DUUIComposer composer;
    private JCas jcas;

    public DUUIInteractions() throws IOException, URISyntaxException, UIMAException, SAXException {
        initialize();
        makeCAS();
    }

    private void makeCAS() throws UIMAException {
        this.jcas = JCasFactory.createJCas();
    }

    public void addView(ApplicationEnums.MODALITIES modalitiy) throws CASException {
        System.out.println("test");
        this.jcas.createView(DUUIProperties.getInstance().getView(modalitiy));
        System.out.println("created view for " +modalitiy);
    }

    private void initialize() throws IOException, URISyntaxException, UIMAException, SAXException {
        DUUILuaContext luaContext = new DUUILuaContext().withJsonLibrary();
        this.composer = new DUUIComposer()
                .withSkipVerification(true)
                .withLuaContext(luaContext)
                .withWorkers(1);

        DUUIRemoteDriver remoteDriver = new DUUIRemoteDriver();
        DUUIDockerDriver dockerDriver = new DUUIDockerDriver();
        DUUIUIMADriver uimaDriver = new DUUIUIMADriver();
        this.composer.addDriver(remoteDriver, dockerDriver, uimaDriver);


    }
    public void sanityCheck(){
        System.out.println("Drivers: " + this.composer.get_drivers());
        System.out.println("Components: "+ this.composer.get_pipeline());
        System.out.println("Instantiated COmponents: " + this.composer.get_instantiatedPipeline());
    }
    public void addComponent(Component component) throws UIMAException, CompressorException, IOException, SAXException {
        this.composer.add(component.getRemoteComponent());
        System.out.println("added component");
//        System.out.println(this.composer.get_pipeline());
    }

    public void addComponents(List<Component> components) throws UIMAException, CompressorException, IOException, SAXException {
        for (Component component : components) {
            addComponent(component);
        }
    }

    public void addXMIWriter() throws ResourceInitializationException, IOException, URISyntaxException, SAXException, CompressorException, InvalidXMLException {
        // writing you data in xmi format
        this.composer.add(new DUUIUIMADriver.Component(createEngineDescription(XmiWriter.class,
                XmiWriter.PARAM_TARGET_LOCATION, "anonymizer/tmpdata",
                XmiWriter.PARAM_PRETTY_PRINT, true,
                XmiWriter.PARAM_OVERWRITE, true,
                XmiWriter.PARAM_VERSION, "1.1"
        )).build());


    }
    public JCas getJcas() {
        return jcas;
    }

    public void setJCas(JCas jcas) {
        this.jcas = jcas;
    }

    public JCas run() throws Exception {
        System.out.println("Running Pipeline");
        this.composer.run(this.jcas);
        return jcas;
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
