package com.abevilacqua.tacoreactive.service;

import com.abevilacqua.tacoreactive.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IngredientService {

  private WebClient.Builder wcBuilder;

  @Autowired
  public IngredientService(WebClient.Builder wcBuilder) {
    this.wcBuilder = wcBuilder;
  }

  public Flux<Ingredient> getIngredients() {
    return wcBuilder.build()
        .get()
        .uri("http://TACO-API/ingredients")
        .retrieve()
        .bodyToFlux(Ingredient.class);
  }

  public Mono<Ingredient> getIngredientById(String id) {
    return wcBuilder.build()
        .get()
        .uri("http://TACO-API/ingredients/{id}", id)
        .retrieve()
        .bodyToMono(Ingredient.class);
  }
}
