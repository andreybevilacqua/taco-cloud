package com.abevilacqua.tacoreactive.controller;

import com.abevilacqua.tacoreactive.client.IngredientClient;
import com.abevilacqua.tacoreactive.model.Ingredient;
import com.abevilacqua.tacoreactive.repo.IngredientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "ingredients")
public class IngredientController {

  private IngredientRepository ingredientRepository;
  private IngredientClient ingredientClient;

  public IngredientController(IngredientRepository ingredientRepository,
                              IngredientClient ingredientClient) {
    this.ingredientRepository = ingredientRepository;
    this.ingredientClient = ingredientClient;
  }

  @GetMapping()
  public Flux<Ingredient> getIngredients() {
    return ingredientRepository.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Ingredient> getIngredientById(long id) {
    return ingredientRepository.findById(id);
  }

  @GetMapping("/APIRequestGetIngredients")
  public void requestIngredients() {
    ingredientClient.requestIngredients();
  }

  @GetMapping("/APIRequestGetIngredient")
  public void requestIngredient(@PathVariable("id") long id) {
    ingredientClient.requestIngredient(id);
  }
}
