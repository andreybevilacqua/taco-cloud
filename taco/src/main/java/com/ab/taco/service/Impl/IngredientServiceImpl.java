package com.ab.taco.service.Impl;

import com.ab.taco.model.Ingredient;
import com.ab.taco.service.IngredientService;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collection;

@Service
@NoArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private RestTemplate restTemplate;
    private Traverson traverson;

    public IngredientServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.traverson = new Traverson(URI.create("http://localhost:8080/ingredients"), MediaTypes.HAL_JSON);
    }

    @Override
    public Collection<Ingredient> getIngredientsWithTraverson(String id) {
        ParameterizedTypeReference<Resources<Ingredient>> ingredientType = new ParameterizedTypeReference<Resources<Ingredient>>() {};
        Resources<Ingredient> ingredientRes = traverson.follow("ingredients").toObject(ingredientType);

        return ingredientRes.getContent();
    }

    @Override
    public Ingredient getIngredientById(String id) {
        return restTemplate.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, id);
    }

    @Override
    public Ingredient getIngredientById(String id, boolean response) {
        ResponseEntity<Ingredient> responseEntity = restTemplate.getForEntity("http://localhost:8080/ingredients/{id}", Ingredient.class, id);
        System.out.println("Fetched time: " + responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) { // Traverson and RestTemplate together.
        String ingredientsUrl = traverson.follow("ingredients")
                .asLink()
                .getHref();
        return restTemplate.postForObject(ingredientsUrl, ingredient, Ingredient.class);
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        return restTemplate.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);
    }

    @Override
    public URI createIngredient(Ingredient ingredient, boolean uri) {
        return restTemplate.postForLocation("http://localhost:8080/ingredients", ingredient);
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient, boolean response, boolean again) {
        ResponseEntity<Ingredient> responseEntity =
                restTemplate.postForEntity("http://localhost:8080/ingredients",
                        ingredient,
                        Ingredient.class);
        System.out.println("New resource created at " + responseEntity.getHeaders().getLocation());
        return responseEntity.getBody();
    }

    @Override
    public void updateIngredient(Ingredient ingredient) {
        restTemplate.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
    }

    @Override
    public void deleteIngredient(Ingredient ingredient) {
        restTemplate.delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
    }
}
