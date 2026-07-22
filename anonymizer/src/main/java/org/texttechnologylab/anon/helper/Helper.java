package org.texttechnologylab.anon.helper;

import org.apache.uima.cas.SerialFormat;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.CasIOUtils;
import org.texttechnologylab.anon.controllers.MainController;
import org.texttechnologylab.anon.exceptions.BadInputException;
import org.texttechnologylab.anon.exceptions.MissingAnonTypeException;
import org.texttechnologylab.anon.exceptions.MissingModalityException;
import org.texttechnologylab.anon.exceptions.MissingUriException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Helper {

    public record ProcessRequest(
            String modality,
            String textinput,
            String fileinput,
            Boolean views,
            List<String> methods,
            Map<String, String> parameters,
            String duuicomponent,
            String duuiurl
    ){}
    //TODO EXTEND FOR Other mod
    public record ProcessResponse(
            String text
    ){}

    /**
     * simple checker to make sure no empty values are passed
     * @param request
     */
    public static void checkIntegrity(ProcessRequest request){
        if(request.modality == null || request.modality.isBlank() ){
            throw new MissingModalityException("Please select a Modality!");
        }
        if(request.methods.isEmpty()){
            throw new MissingAnonTypeException("Please select at least one Anonymization Type");
        }
        if(request.duuiurl == null || request.duuiurl.isBlank()){
            throw new MissingUriException("No remote or docker URI passed, please provide one!");
        }
        if (request.textinput == null || request.textinput.isBlank()) {
            throw new BadInputException("Nothing to annotate!");
        }
    }


    public static ProcessRequest stripParameters(ProcessRequest request){
        Map<String,String> parameters = request.parameters();
        parameters.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue().isBlank());
        return request;
    }

    public static void simpleTempExporter(JCas cas, String filename) throws IOException, IOException {
        File outputFile = new File("anonymizer/tmpdata/xmi/" + filename + ".xmi");
        System.out.println("exporting to: "+ outputFile.getAbsolutePath());
        outputFile.getParentFile().mkdirs();
        CasIOUtils.save(cas.getCas(), new FileOutputStream(outputFile), SerialFormat.XMI_PRETTY);
    }


}
