package com.abevilacqua.tacokitchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class TacoKitchenApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoKitchenApplication.class, args);
	}

}
