package com.abevilacqua.tacohystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class TacoHystrixDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoHystrixDashboardApplication.class, args);
	}

}
