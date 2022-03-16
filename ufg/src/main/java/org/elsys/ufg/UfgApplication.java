package org.elsys.ufg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class UfgApplication {
	public static void main(String[] args) {
		SpringApplication.run(UfgApplication.class, args);
	}
}
