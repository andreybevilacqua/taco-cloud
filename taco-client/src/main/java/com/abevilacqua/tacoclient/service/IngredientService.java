package com.abevilacqua.tacoclient.service;

import com.abevilacqua.tacoclient.model.Ingredient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class IngredientService {

  private RestTemplate restTemplate;

  @Autowired
  public IngredientService(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

  @HystrixCommand(fallbackMethod = "getDefaultIngredients",
      commandProperties = {
      @HystrixProperty(
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
  public List getIngredients() {
    return restTemplate.getForObject("http://TACO-API/ingredients/", List.class);
  }

  @HystrixCommand(fallbackMethod = "getDefaultIngredient",
  commandProperties = {
      @HystrixProperty(
          name = "execution.timeout.enabled",
          value = "false"
      )
  })
  public Ingredient getIngredientById(String id) {
    return restTemplate.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, id);
  }

  public Ingredient createIngredient(Ingredient ingredient) {
    return restTemplate.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);
  }

  public void updateIngredient(Ingredient ingredient) {
    restTemplate.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
  }

  public void deleteIngredient(String id) {
    restTemplate.delete("http://localhost:8080/ingredients/{id}", id);
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
