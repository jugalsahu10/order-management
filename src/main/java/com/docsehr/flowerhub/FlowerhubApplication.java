package com.docsehr.flowerhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlowerhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerhubApplication.class, args);
	}

}
