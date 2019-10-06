package com.ab.taco;

import com.ab.taco.repo.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

import static com.ab.taco.config.DBInitializer.initialize;

@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
public class TacoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoApplication.class, args);
	}

	@Bean
	public ApplicationRunner init(IngredientRepository ingrRepo,
								  TacoRepository tacoRepo,
								  OrderRepository orderRepo,
								  UserRepository userRepo,
								  UserAuthoritiesRepository userAuthRepo) {
		return args -> initialize(ingrRepo, tacoRepo, orderRepo, userRepo, userAuthRepo);
	}
}
