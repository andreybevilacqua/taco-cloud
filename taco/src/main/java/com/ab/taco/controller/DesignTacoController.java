package com.ab.taco.controller;

import com.ab.taco.controller.api.TacoResource;
import com.ab.taco.controller.api.TacoResourceAssembler;
import com.ab.taco.model.Ingredient;
import com.ab.taco.model.Ingredient.Type;
import com.ab.taco.model.Order;
import com.ab.taco.model.Taco;
import com.ab.taco.repo.IngredientRepository;
import com.ab.taco.repo.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/design", produces = {"application/json", "text/xml"})
@SessionAttributes("order")
@CrossOrigin(origins = "*")
public class DesignTacoController {

    private IngredientRepository ingredientRepo;

    private TacoRepository tacoRepository;

    @ModelAttribute
    public Taco design() { return new Taco(); }

    @ModelAttribute
    public Order order() { return new Order(); }

    public DesignTacoController(TacoRepository tacoRepository, IngredientRepository ingredientRepository) {
        this.tacoRepository = tacoRepository;
        this.ingredientRepo = ingredientRepository;
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);

        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        return "design";
    }

    @GetMapping("/recent")
    public Resources<TacoResource> recentTaco() {
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

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }


}
