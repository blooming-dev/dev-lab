package com.devlab.api;

import com.devlab.core.common.EnableCore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCore
@SpringBootApplication
public class LabApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabApiApplication.class, args);
	}

}
