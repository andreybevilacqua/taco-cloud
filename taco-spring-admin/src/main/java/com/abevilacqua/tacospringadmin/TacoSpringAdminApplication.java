package com.abevilacqua.tacospringadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class TacoSpringAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoSpringAdminApplication.class, args);
	}

}
