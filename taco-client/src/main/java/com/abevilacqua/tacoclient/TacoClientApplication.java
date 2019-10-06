package com.abevilacqua.tacoclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class TacoClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoClientApplication.class, args);
	}

}
