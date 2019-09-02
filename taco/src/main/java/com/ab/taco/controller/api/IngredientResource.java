package com.ab.taco.controller.api;

import com.ab.taco.model.Ingredient;
import com.ab.taco.model.Ingredient.Type;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

class IngredientResource extends ResourceSupport {

    @Getter
    private String name;

    @Getter
    private Type type;

    IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
