package org.texttechnologylab.anon.helper;

import org.texttechnologylab.anon.controllers.MainController;
import org.texttechnologylab.anon.exceptions.BadInputException;
import org.texttechnologylab.anon.exceptions.MissingAnonTypeException;
import org.texttechnologylab.anon.exceptions.MissingModalityException;
import org.texttechnologylab.anon.exceptions.MissingUriException;

import java.util.List;
import java.util.Map;

public class InputHelper {

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

}
