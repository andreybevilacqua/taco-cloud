package com.abevilacqua.tacoreactive.config;

import com.abevilacqua.tacoreactive.model.Ingredient;
import com.abevilacqua.tacoreactive.model.Taco;
import com.abevilacqua.tacoreactive.repo.IngredientRepository;
import com.abevilacqua.tacoreactive.repo.TacoRepository;

import java.util.stream.Stream;

import static com.abevilacqua.tacoreactive.model.Ingredient.Type.*;

public class DBInitializer {

  public static void initialize(TacoRepository tacoRepo,
                                IngredientRepository ingrRepo) {
    Stream.of(
        new Ingredient("Flour Tortilla", WRAP),
        new Ingredient("Corn Tortilla", WRAP),
        new Ingredient("Ground Beef", PROTEIN),
        new Ingredient("Carnitas", PROTEIN),
        new Ingredient("Diced Tomatoes", VEGGIES),
        new Ingredient("Lettuce", VEGGIES),
        new Ingredient("Cheddar", CHEESE),
        new Ingredient("Monterrey Jack", CHEESE),
        new Ingredient("Sour Cream", SAUCE))
        .forEach(ingrRepo::save);

    Stream.of(
        Taco.builder().name("Taco 1").ingredients(ingrRepo.findAll()).build(),
        Taco.builder().name("Taco 2").ingredients(ingrRepo.findAll()).build(),
        Taco.builder().name("Taco 3").ingredients(ingrRepo.findAll()).build(),
        Taco.builder().name("Taco 4").ingredients(ingrRepo.findAll()).build(),
        Taco.builder().name("Taco 5").ingredients(ingrRepo.findAll()).build())
        .forEach(tacoRepo::save);
  }
}
