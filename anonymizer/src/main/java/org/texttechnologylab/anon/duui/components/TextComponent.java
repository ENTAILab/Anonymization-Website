package org.texttechnologylab.anon.duui.components;

import org.texttechnologylab.DockerUnifiedUIMAInterface.DUUIComposer;
import org.texttechnologylab.anon.config.DUUIProperties;

import java.util.List;


public class TextComponent extends Component{
    List<String> parameters = duuiProperties.getTextAnonTypes();

    TextComponent() {
        super("TEXT");
    }


    @Override
    public void addToComposer(DUUIComposer composer) {

    }
}
