package com.abevilacqua.tacoclient.service;

import com.abevilacqua.tacoclient.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class IngredientService {

  private RestTemplate restTemplate;

  @Autowired
  public IngredientService(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

  public List getIngredients() {
    return restTemplate.getForObject("http://TACO-API/ingredients/", List.class);
  }

  public Ingredient getIngredientById(String id) {
    return restTemplate.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, id);
  }

  public Ingredient createIngredient(Ingredient ingredient) {
    return restTemplate.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);
  }

  public void updateIngredient(Ingredient ingredient) {
    restTemplate.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
  }

  public void deleteIngredient(String id) {
    restTemplate.delete("http://localhost:8080/ingredients/{id}", id);
  }

}
