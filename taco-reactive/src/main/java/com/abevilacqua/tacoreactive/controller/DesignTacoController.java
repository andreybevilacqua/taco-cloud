package com.abevilacqua.tacoreactive.controller;

import com.abevilacqua.tacoreactive.model.Taco;
import com.abevilacqua.tacoreactive.repo.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/design")
public class DesignTacoController {

  private TacoRepository tacoRepo;

  @Autowired
  public DesignTacoController(TacoRepository tacoRepo) {
    this.tacoRepo = tacoRepo;
  }

  @GetMapping("/recent")
  public Flux<Taco> recentTacos() {
    return tacoRepo.findAll().take(12);
  }

  @GetMapping("${id}")
  public Mono<Taco> tacoById(@PathVariable("id") Long id) {
    return tacoRepo.findById(id);
  }

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Taco> createTaco(@RequestBody Mono<Taco> tacoMono) {
    return tacoRepo.saveAll(tacoMono).next();
  }


}
