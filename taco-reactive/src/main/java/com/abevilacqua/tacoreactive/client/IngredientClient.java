package com.abevilacqua.tacoreactive.client;

import com.abevilacqua.tacoreactive.model.Ingredient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class IngredientClient {

  public void requestIngredient(@PathVariable("id") long id) {
    Mono<Ingredient> ingredientMono = WebClient.create()
        .get()
        .uri("http://localhost:8080/ingredients/{id}", id)
        .retrieve()
        .bodyToMono(Ingredient.class);

    ingredientMono.subscribe(i -> System.out.println(i.getName()));
  }

  public void requestIngredients() {
    Flux<Ingredient> ingredients = WebClient.create()
        .get()
        .uri("http://localhost:8080/ingredients")
        .retrieve()
        .bodyToFlux(Ingredient.class);

    ingredients.subscribe(i -> System.out.println(i.getName()));
  }
}
