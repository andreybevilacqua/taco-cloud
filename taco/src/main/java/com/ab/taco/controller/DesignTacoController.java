package com.ab.taco.controller;

import com.ab.taco.controller.api.TacoResource;
import com.ab.taco.controller.api.TacoResourceAssembler;
import com.ab.taco.model.Taco;
import com.ab.taco.repo.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/design", produces = {"application/json", "text/xml"})
@CrossOrigin(origins = "*")
public class DesignTacoController {

    private final TacoRepository tacoRepository;

    @Value("${some.specific.Taco-API.configuration}")
    private String mySpecificConfiguration;

    @Value("${greeting.message}")
    private String myGreetingMessage;

    @Autowired
    public DesignTacoController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping("/recent")
    public Resources<TacoResource> recentTaco() {
        // Attribute and object received from Config Server
        log.info("My specific configuration is: {}", mySpecificConfiguration);
        log.info("This is my property from Config Server: {}", myGreetingMessage);

        Pageable page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = new ArrayList<>();
        tacoRepository.findAll(page).forEach(tacos::add);

        List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);

        Resources<TacoResource> recentResources = new Resources<>(tacoResources);
        recentResources.add(linkTo(
                methodOn(DesignTacoController.class)
                .recentTaco())
                .withRel("recents"));

        return recentResources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> taco = tacoRepository.findById(id);

        return taco.map(t -> new ResponseEntity<>(taco.get(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity postTaco(@RequestBody Taco taco) {
        log.info("Processing design: " + taco.toString());
        tacoRepository.save(taco);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
