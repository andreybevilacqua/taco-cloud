package com.abevilacqua.tacoreactive.controller;

import com.abevilacqua.tacoreactive.model.Ingredient;
import com.abevilacqua.tacoreactive.model.Taco;
import com.abevilacqua.tacoreactive.repo.TacoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = DesignTacoController.class)
@ContextConfiguration(classes = DesignTacoController.class)
public class DesignTacoControllerIntegrationTest {

  @Autowired
  private WebTestClient testClient;

  @MockBean
  private TacoRepository tacoRepo;

  @Test
  public void shouldReturnRecentTacos() throws IOException {
    Taco[] tacos = {
        createTaco(1L), createTaco(2L), createTaco(3L), createTaco(4L),
        createTaco(5L), createTaco(6L), createTaco(7L), createTaco(8L),
        createTaco(9L), createTaco(10L), createTaco(11L), createTaco(12L)};

    Mockito.when(tacoRepo.findAll()).thenReturn(Flux.fromArray(tacos));

    testClient.get().uri("/design/recent")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Taco.class).contains(tacos);
  }

  private Taco createTaco(Long number) {
    Taco taco = new Taco();
    taco.setId(number);
    taco.setName("Taco " + number);
    List<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(new Ingredient(1L, "INGA", Ingredient.Type.WRAP));
    ingredients.add(new Ingredient(2L, "INGB", Ingredient.Type.PROTEIN));

    taco.setIngredients(Flux.fromIterable(ingredients));
    return taco;
  }
}
