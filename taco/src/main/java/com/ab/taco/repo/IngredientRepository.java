package com.ab.taco.repo;

import com.ab.taco.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    Ingredient findById(Long id);

}
