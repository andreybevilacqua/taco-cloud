package com.abevilacqua.tacoreactive;

import com.abevilacqua.tacoreactive.repo.IngredientRepository;
import com.abevilacqua.tacoreactive.repo.TacoRepository;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import static com.abevilacqua.tacoreactive.config.DBInitializer.initialize;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class TacoReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoReactiveApplication.class, args);
	}

	@Bean
	public ApplicationRunner init(TacoRepository tacoRepo,
								  IngredientRepository ingrRepo) {
		return args -> initialize(tacoRepo, ingrRepo);
	}

}
