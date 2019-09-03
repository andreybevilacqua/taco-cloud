package com.abevilacqua.tacoreactive.controller;

import com.abevilacqua.tacoreactive.model.Ingredient;
import com.abevilacqua.tacoreactive.repo.IngredientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "ingredients")
public class IngredientController {

  private IngredientRepository ingredientRepository;

  public IngredientController(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  @GetMapping("/{id}")
  public Mono<Ingredient> getIngredientById(long id) {
    return ingredientRepository.findById(id);
  }

  @GetMapping("/APIRequest")
  public void requestIngredient(long id) {
    Mono<Ingredient> ingredientMono = WebClient.create()
        .get()
        .uri("http://localhost:8080/ingredients/{id}", id)
        .retrieve()
        .bodyToMono(Ingredient.class);

    ingredientMono.subscribe(i -> System.out.println(i.getName()));
  }
}
