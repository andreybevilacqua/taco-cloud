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
                new Ingredient(1L, "Flour Tortilla", WRAP),
                new Ingredient(2L,  "Corn Tortilla", WRAP),
                new Ingredient(3L,  "Ground Beef", PROTEIN),
                new Ingredient( 4L, "Carnitas", PROTEIN),
                new Ingredient( 5L, "Diced Tomatoes", VEGGIES),
                new Ingredient( 6L, "Lettuce", VEGGIES),
                new Ingredient( 7L, "Cheddar", CHEESE),
                new Ingredient( 8L, "Monterrey Jack", CHEESE),
                new Ingredient( 9L, "Sour Cream", SAUCE))
                .forEach(ingrRepo::save);

        Stream.of(
                Taco.builder().id(1L).name("Taco 1").ingredients(ingrRepo.findAll()).build(),
                Taco.builder().id(2L).name("Taco 2").ingredients(ingrRepo.findAll()).build(),
                Taco.builder().id(3L).name("Taco 3").ingredients(ingrRepo.findAll()).build(),
                Taco.builder().id(4L).name("Taco 4").ingredients(ingrRepo.findAll()).build(),
                Taco.builder().id(5L).name("Taco 5").ingredients(ingrRepo.findAll()).build())
                .forEach(tacoRepo::save);
    }
}
