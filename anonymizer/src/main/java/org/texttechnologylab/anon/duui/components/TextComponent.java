package org.texttechnologylab.anon.duui.components;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.uima.util.InvalidXMLException;
import org.texttechnologylab.DockerUnifiedUIMAInterface.DUUIComposer;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.IDUUIDriverInterface;
import org.texttechnologylab.anon.config.DUUIProperties;
import org.texttechnologylab.anon.config.enums.ApplicationEnums;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TextComponent extends Component{
    List<String> parameters = duuiProperties.getTextAnonProperties();


    TextComponent() throws URISyntaxException, IOException {
        super("TEXT");
    }






    @Override
    public void removePropertiesFromComponent() {

    }

    @Override
    public void addToComposer(DUUIComposer composer) throws CompressorException, InvalidXMLException, IOException, SAXException {
        composer.add(this.remoteComponent);
    }
}
