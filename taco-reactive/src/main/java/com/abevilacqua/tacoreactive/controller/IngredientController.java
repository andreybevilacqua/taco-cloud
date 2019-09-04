package com.abevilacqua.tacoreactive.controller;

import com.abevilacqua.tacoreactive.client.IngredientClient;
import com.abevilacqua.tacoreactive.model.Ingredient;
import com.abevilacqua.tacoreactive.repo.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "ingredients")
public class IngredientController {

  private IngredientRepository ingredientRepository;
  private IngredientClient ingredientClient;

  @Autowired
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

  @PostMapping()
  public Mono<Ingredient> createIngredient(Mono<Ingredient> ingredient) {
    return ingredientRepository.saveAll(ingredient).next();
  }

  @GetMapping("/APIRequestGetIngredients")
  public void requestIngredients() {
    ingredientClient.requestIngredients();
  }

  @GetMapping("/APIRequestGetIngredient")
  public void requestIngredient(@PathVariable("id") long id) {
    ingredientClient.requestIngredient(id);
  }

  @GetMapping("/APIRequestPostIngredient")
  public void requestCreateIngredient(@RequestBody Mono<Ingredient> ingredient) { ingredientClient.createIngredient(ingredient); }
}
