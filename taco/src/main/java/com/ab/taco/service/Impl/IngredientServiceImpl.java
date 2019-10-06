package com.ab.taco.service.Impl;

import com.ab.taco.model.Ingredient;
import com.ab.taco.service.IngredientService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
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
    @HystrixCommand(fallbackMethod = "getDefaultIngredients",
        commandProperties = {
            @HystrixProperty( // Timeout to call the fallback method if this takes more than half second.
                name = "execution.isolation.thread.timeoutInMilliseconds",
                value = "500"
            ),
            @HystrixProperty( // Method must be invoked more than 30 times
                name = "circuitBreaker.requestVolumeThreshold",
                value = "30"
            ),
            @HystrixProperty( // Fail more than 25% of the time
                name = "circuitBreaker.errorThresholdPercentage",
                value = "25"
            ),
            @HystrixProperty( // Within 20 seconds.
                name = "metrics.rollingStats.timeInMilliseconds",
                value = "20000"
            ),
            @HystrixProperty( // Must remain open for 1 minute before becoming half open.
                name = "circuitBreaker.sleepWindowInMilliseconds",
                value = "60000"
            )})
    public Collection<Ingredient> getIngredientsWithTraverson(String id) {
        ParameterizedTypeReference<Resources<Ingredient>> ingredientType = new ParameterizedTypeReference<Resources<Ingredient>>() {};
        Resources<Ingredient> ingredientRes = traverson.follow("ingredients").toObject(ingredientType);

        return ingredientRes.getContent();
    }

    @Override
    @HystrixCommand(fallbackMethod = "getDefaultIngredient",
    commandProperties = {
        @HystrixProperty( // Method must be invoked more than 30 times
            name = "circuitBreaker.requestVolumeThreshold",
            value = "30"
        ),
        @HystrixProperty( // Fail more than 15% of the time
            name = "circuitBreaker.errorThresholdPercentage",
            value = "15"
        ),
        @HystrixProperty( // Within 20 seconds.
            name = "metrics.rollingStats.timeInMilliseconds",
            value = "20000"
        ),
        @HystrixProperty( // Must remain open for 1 minute before becoming half open.
            name = "circuitBreaker.sleepWindowInMilliseconds",
            value = "60000"
        ),
    })
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

    private Iterable<Ingredient> getDefaultIngredients() {
        return Arrays.asList(
            new Ingredient("Flour Tortilla", Ingredient.Type.WRAP),
            new Ingredient("Ground Beef", Ingredient.Type.PROTEIN),
            new Ingredient("Shredded Cheddar", Ingredient.Type.CHEESE));
    }

    private Ingredient getDefaultIngredient() {
        return new Ingredient("Flour Tortilla", Ingredient.Type.WRAP);
    }
}
