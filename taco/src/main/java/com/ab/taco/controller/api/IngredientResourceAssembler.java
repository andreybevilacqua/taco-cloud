package com.ab.taco.controller.api;

import com.ab.taco.controller.IngredientController;
import com.ab.taco.model.Ingredient;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

class IngredientResourceAssembler extends ResourceAssemblerSupport<Ingredient, IngredientResource> {

    IngredientResourceAssembler() {
        super(IngredientController.class, IngredientResource.class);
    }

    @Override
    protected IngredientResource instantiateResource(Ingredient ingredient) {
        return new IngredientResource(ingredient);
    }

    @Override
    public IngredientResource toResource(Ingredient ingredient) {
        return createResourceWithId(ingredient.getId(), ingredient);
    }
}
