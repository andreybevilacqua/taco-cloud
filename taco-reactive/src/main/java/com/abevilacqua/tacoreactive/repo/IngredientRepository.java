package com.abevilacqua.tacoreactive.repo;

import com.abevilacqua.tacoreactive.model.Ingredient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, Long> {
}
