package org.texttechnologylab.anon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// https://medium.com/@alexandre.therrien3/java-spring-tutorial-the-only-tutorial-you-will-need-to-get-started-vs-code-13413e661db5
// https://spring.io/guides/tutorials/rest

@EnableAutoConfiguration
@Configuration
@ComponentScan("org.texttechnology.anon") // so that all files in the folder will be recognized by Spring
public class AnonymizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnonymizerApplication.class, args);

		System.out.println("port:http://localhost:8080/home.html ");
	}

}


