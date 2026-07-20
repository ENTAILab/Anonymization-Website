package org.texttechnologylab.anon.duui;

import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIRemoteDriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.IDUUIDriverInterface;
import org.texttechnologylab.anon.data.Input;

public class DUUIInteractions {


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
