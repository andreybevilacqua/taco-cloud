package com.ab.taco.controller;

import com.ab.taco.model.Ingredient;
import com.ab.taco.repo.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ingredients")
@CrossOrigin(origins = "*")
public class IngredientController {

    private final IngredientRepository repository;

    @Autowired
    public IngredientController(IngredientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        repository.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @GetMapping("/{id}")
    public Ingredient getById(@PathVariable("id") final String id) {
        return repository.findById(Long.valueOf(id));
    }
}
