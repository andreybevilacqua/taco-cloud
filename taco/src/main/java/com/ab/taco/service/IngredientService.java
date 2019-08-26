package com.ab.taco.service;

import com.ab.taco.model.Ingredient;

import java.net.URI;
import java.util.Collection;

public interface IngredientService {

    Collection<Ingredient> getIngredientsWithTraverson(String id);

    Ingredient getIngredientById(String id);

    Ingredient getIngredientById(String id, boolean response);

    Ingredient addIngredient(Ingredient ingredient);

    Ingredient createIngredient(Ingredient ingredient);

    URI createIngredient(Ingredient ingredient, boolean uri);

    Ingredient createIngredient(Ingredient ingredient, boolean response, boolean again);

    void updateIngredient(Ingredient ingredient);

    void deleteIngredient(Ingredient ingredient);
}
