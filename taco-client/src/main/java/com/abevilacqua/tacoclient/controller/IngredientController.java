package com.abevilacqua.tacoclient.controller;

import com.abevilacqua.tacoclient.model.Ingredient;
import com.abevilacqua.tacoclient.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
@CrossOrigin(origins = "*")
public class IngredientController {

  private IngredientService service;

  @Autowired
  public IngredientController(IngredientService ingredientService) { this.service = ingredientService; }

  @GetMapping
  public List getIngredienst() {
    return service.getIngredients();
  }

  @GetMapping("/{id}")
  public Ingredient getById(@PathVariable("id") final String id) {
    return service.getIngredientById(id);
  }

  @PostMapping
  public Ingredient createIngredient(@RequestBody Ingredient ingredient) { return service.createIngredient(ingredient); }

  @PutMapping
  public void updateIngredient(@RequestBody Ingredient ingredient) { service.updateIngredient(ingredient); }

  @DeleteMapping
  public void deleteIngredient(@PathVariable("id") final String id) { service.deleteIngredient(id);}

}
