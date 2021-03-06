package com.abevilacqua.tacoreactive.client;

import com.abevilacqua.tacoreactive.exception.ClientErrorException;
import com.abevilacqua.tacoreactive.exception.UnknownIngredientException;
import com.abevilacqua.tacoreactive.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

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
        .onStatus(
            HttpStatus::is4xxClientError, // Or status -> status == HttpStatus.NOT_FOUND
            response -> Mono.just(new UnknownIngredientException()))
        .onStatus(
            HttpStatus::is5xxServerError,
            response -> Mono.just(new ClientErrorException())
        )
        .bodyToMono(Ingredient.class);

    ingredientMono.subscribe(
        i -> System.out.println(i.getName()),
        e -> System.out.println(e));
  }

  public void requestIngredients() {
    Flux<Ingredient> ingredients = webClient
        .get()
        .uri("/ingredients")
        .retrieve()
        .bodyToFlux(Ingredient.class);

    ingredients
        .timeout(Duration.ofSeconds(1))
        .subscribe(
            i -> System.out.println(i.getName()),
            e -> System.out.println(e));
  }

  public void createIngredient(Mono<Ingredient> ingredient) {
    Mono<Ingredient> result = webClient.post()
        .uri("/ingredients")
        .body(ingredient, Ingredient.class)
        .retrieve()
        .bodyToMono(Ingredient.class);

    result.subscribe(System.out::println);
  }

  public void createIngredient(Ingredient ingredient) {
    Mono<Ingredient> result = webClient.post()
        .uri("/ingredients")
        .syncBody(ingredient)// If you have a domain object and not a Mono/Flux.
        .retrieve()
        .bodyToMono(Ingredient.class);

    result.subscribe(System.out::println);
  }

  public void updateIngredient(Ingredient ingredient) {
    webClient.put()
        .uri("/ingredients/{id}", ingredient.getId())
        .syncBody(ingredient)// If you have a domain object and not a Mono/Flux.
        .retrieve()
        .bodyToMono(Void.class)
        .subscribe();
  }

  public void deleteIngredient(Long id) {
    webClient.delete()
        .uri("/ingredients/{id}", id)
        .retrieve()
        .bodyToMono(Void.class)
        .subscribe();
  }

  public void requestIngredientWithExchange() {
    Mono<Ingredient> ingredient = webClient
        .get()
        .uri("/ingredients")
        .exchange()
        .flatMap(cr -> {
          if(cr.headers().header("X_UNAVAILABLE").contains("true")) return Mono.empty();
          else return Mono.just(cr);
        })
        .flatMap(cr -> cr.bodyToMono(Ingredient.class));
  }
}
