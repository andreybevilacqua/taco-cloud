package com.abevilacqua.tacoclient.controller;

import com.abevilacqua.tacoclient.model.Ingredient;
import com.abevilacqua.tacoclient.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
@CrossOrigin(origins = "*")
public class IngredientController {

  private IngredientService service;

  @Autowired
  public IngredientController(IngredientService ingredientService) { this.service = ingredientService; }

  @GetMapping("/{id}")
  public Ingredient getById(@PathVariable("id") final String id) {
    return service.getIngredientById(id);
  }
}
