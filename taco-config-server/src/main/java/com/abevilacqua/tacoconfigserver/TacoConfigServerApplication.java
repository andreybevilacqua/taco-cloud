package com.abevilacqua.tacoconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class TacoConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoConfigServerApplication.class, args);
	}

}
