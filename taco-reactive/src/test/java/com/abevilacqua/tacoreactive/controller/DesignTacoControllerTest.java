package com.abevilacqua.tacoreactive.controller;

import com.abevilacqua.tacoreactive.model.Ingredient;
import com.abevilacqua.tacoreactive.model.Taco;
import com.abevilacqua.tacoreactive.repo.TacoRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class DesignTacoControllerTest {

  @Mock
  private TacoRepository tacoRepository;

  private WebTestClient testClient;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    testClient = WebTestClient
        .bindToController(new DesignTacoController(tacoRepository))
        .build();
  }

  @Test
  public void shouldReturnRecentTacos() {
    Taco[] tacos = {
        testTaco(1L), testTaco(2L),
        testTaco(3L), testTaco(4L),
        testTaco(5L), testTaco(6L),
        testTaco(7L), testTaco(8L),
        testTaco(9L), testTaco(10L),
        testTaco(11L), testTaco(12L),
        testTaco(13L), testTaco(14L),
        testTaco(15L), testTaco(16L)
    };

    Flux<Taco> tacoFlux = Flux.just(tacos);

    when(tacoRepository.findAll()).thenReturn(tacoFlux);

    testClient.get().uri("/design/recent")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$").isArray()
        .jsonPath("$").isNotEmpty()
        .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
        .jsonPath("$[0].name").isEqualTo("Taco 1").jsonPath("$[1].id")
        .isEqualTo(tacos[1].getId().toString()).jsonPath("$[1].name")
        .isEqualTo("Taco 2").jsonPath("$[11].id")
        .isEqualTo(tacos[11].getId().toString())
        .jsonPath("$[11].name").isEqualTo("Taco 12").jsonPath("$[12]").doesNotExist()
        .jsonPath("$[12]").doesNotExist();
  }

  private Taco testTaco(Long number) {
    Taco taco = new Taco();
    taco.setId(number);
    taco.setName("Taco " + number);
    List<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(new Ingredient("INGA", Ingredient.Type.WRAP));
    ingredients.add(new Ingredient("INGB", Ingredient.Type.PROTEIN));

    taco.setIngredients(Flux.fromIterable(ingredients));
    return taco;
  }
}
