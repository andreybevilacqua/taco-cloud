package com.abevilacqua.tacoreactive.client;

import com.abevilacqua.tacoreactive.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class IngredientClient {

  private WebClient webClient;

  @Autowired
  public IngredientClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public void requestIngredient(@PathVariable("id") long id) {
    Mono<Ingredient> ingredientMono = webClient
        .get()
        .uri("/ingredients/{id}", id)
        .retrieve()
        .bodyToMono(Ingredient.class);

    ingredientMono.subscribe(i -> System.out.println(i.getName()));
  }

  public void requestIngredients() {
    Flux<Ingredient> ingredients = webClient
        .get()
        .uri("/ingredients")
        .retrieve()
        .bodyToFlux(Ingredient.class);

    ingredients.subscribe(i -> System.out.println(i.getName()));
  }
}
