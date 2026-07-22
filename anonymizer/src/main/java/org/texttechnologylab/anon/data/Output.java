package org.texttechnologylab.anon.data;

import org.springframework.stereotype.Service;

@Service
public class Output {
    private String outputText;
    //Todo add for other modalities

    public synchronized void setOutputText(String outputText) {
        this.outputText = outputText;
    }
    public synchronized String getOutputText() {
        return outputText;
    }
}
