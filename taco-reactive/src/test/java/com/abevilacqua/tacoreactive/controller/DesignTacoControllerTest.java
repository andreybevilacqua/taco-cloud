package com.abevilacqua.tacoreactive.controller;

import com.abevilacqua.tacoreactive.model.Ingredient;
import com.abevilacqua.tacoreactive.model.Taco;
import com.abevilacqua.tacoreactive.repo.TacoRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
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
        createTestTaco(1L), createTestTaco(2L),
        createTestTaco(3L), createTestTaco(4L),
        createTestTaco(5L), createTestTaco(6L),
        createTestTaco(7L), createTestTaco(8L),
        createTestTaco(9L), createTestTaco(10L),
        createTestTaco(11L), createTestTaco(12L),
        createTestTaco(13L), createTestTaco(14L),
        createTestTaco(15L), createTestTaco(16L)
    };
    when(tacoRepository.findAll()).thenReturn(Flux.just(tacos));

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

  @Test
  public void shouldSaveTaco() {
    Mono<Taco> unsavedTaco = Mono.just(createTestTaco(1L));

    when(tacoRepository.saveAll(unsavedTaco)).thenReturn(Flux.just(createTestTaco(1L)));

    testClient.post()
        .uri("/design")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(unsavedTaco, Taco.class)
        .exchange()
        .expectStatus().isCreated();
  }

  private Taco createTestTaco(Long number) {
    List<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(new Ingredient("INGA", Ingredient.Type.WRAP));
    ingredients.add(new Ingredient("INGB", Ingredient.Type.PROTEIN));

    return Taco.builder()
        .name("Taco" + number)
        .ingredients(Flux.fromIterable(ingredients))
        .build();
  }
}
