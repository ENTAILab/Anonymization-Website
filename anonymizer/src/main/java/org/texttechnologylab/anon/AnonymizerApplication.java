package org.texttechnologylab.anon;

import org.apache.uima.UIMAException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.texttechnologylab.anon.duui.DUUIInteractions;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;

// https://medium.com/@alexandre.therrien3/java-spring-tutorial-the-only-tutorial-you-will-need-to-get-started-vs-code-13413e661db5
// https://spring.io/guides/tutorials/rest

@EnableAutoConfiguration
@Configuration
@ComponentScan("org.texttechnologylab.anon") // so that all files in the folder will be recognized by Spring
public class AnonymizerApplication {
	public static DUUIInteractions duuiInteractions;

	public static void main(String[] args) throws IOException, URISyntaxException, UIMAException, SAXException {
		duuiInteractions = new DUUIInteractions();
		ConfigurableApplicationContext context = SpringApplication.run(AnonymizerApplication.class, args);

		String port = context.getEnvironment().getProperty("server.port", "8080");
		String address = context.getEnvironment().getProperty("server.address", "localhost");
		System.out.println("Running at: http://" +address+ ":" + port + "/home.html");
	}

}


