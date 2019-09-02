package com.abevilacqua.tacoreactive.repo;

import com.abevilacqua.tacoreactive.model.Ingredient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, Long> {
}
