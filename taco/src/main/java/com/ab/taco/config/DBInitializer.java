package com.ab.taco.config;

import com.ab.taco.model.*;
import com.ab.taco.repo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.ab.taco.model.Ingredient.Type.*;

public class DBInitializer {

    public static void initialize(IngredientRepository ingrRepo,
                                  TacoRepository tacoRepo,
                                  OrderRepository orderRepo,
                                  UserRepository userRepo,
                                  UserAuthoritiesRepository userAuthRepo) {
        userInitializer(userRepo, userAuthRepo);
        initialize(ingrRepo, tacoRepo, orderRepo, userRepo);
    }

    private static void initialize(IngredientRepository ingrRepo,
                                   TacoRepository tacoRepo,
                                   OrderRepository orderRepo,
                                   UserRepository userRepo) {
        Stream.of(
                new Ingredient( "Flour Tortilla", WRAP),
                new Ingredient( "Corn Tortilla", WRAP),
                new Ingredient( "Ground Beef", PROTEIN),
                new Ingredient( "Carnitas", PROTEIN),
                new Ingredient( "Diced Tomatoes", VEGGIES),
                new Ingredient( "Lettuce", VEGGIES),
                new Ingredient( "Cheddar", CHEESE),
                new Ingredient( "Cheddar", CHEESE),
                new Ingredient( "Monterrey Jack", CHEESE),
                new Ingredient( "Sour Cream", SAUCE))
                .forEach(ingrRepo::save);

        Stream.of(
                Taco.builder().id(1L).name("Taco 1").ingredients(getIngredients(ingrRepo)).build(),
                Taco.builder().id(2L).name("Taco 2").ingredients(getIngredients(ingrRepo)).build(),
                Taco.builder().id(3L).name("Taco 3").ingredients(getIngredients(ingrRepo)).build(),
                Taco.builder().id(4L).name("Taco 4").ingredients(getIngredients(ingrRepo)).build(),
                Taco.builder().id(5L).name("Taco 5").ingredients(getIngredients(ingrRepo)).build())
                .forEach(tacoRepo::save);

        Stream.of(
                Order.builder()
                        .name("Order 1")
                        .street("Street 1")
                        .city("City 1")
                        .state("State 1")
                        .zip("Zip 1")
                        .ccNumber("4111111111111111")
                        .ccCVV("123")
                        .ccExpiration("01/20")
                        .tacos(getTacos(tacoRepo))
                        .user(getUser(userRepo))
                        .build(),
                Order.builder()
                        .name("Order 2")
                        .street("Street 2")
                        .city("City 2")
                        .state("State 2")
                        .zip("Zip 2")
                        .ccNumber("4111111111111111")
                        .ccCVV("123")
                        .ccExpiration("02/20")
                        .tacos(getTacos(tacoRepo))
                        .user(getUser(userRepo))
                        .build()
        ).forEach(orderRepo::save);
    }

    private static void userInitializer(UserRepository userRepo, UserAuthoritiesRepository userAuthRepo) {
        Stream.of(
            new User("user", "$2a$10$tbFHVXbIC1xj4/iouBoA2eo9Mm2/SrhBeMhvUrkOKtC6jugv.Kehm",
                    "User", "my_street", "my_city", "my_state", "123", "123", true))
            .forEach(userRepo::save);
        Stream.of(
                new UserAuthorities("user", "USER"),
                new UserAuthorities("user", "ADMIN"))
                .forEach(userAuthRepo::save);
    }

    private static List<Ingredient> getIngredients(IngredientRepository ingrRepo) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingrRepo.findAll().forEach(ingredients::add);
        return ingredients;
    }

    private static List<Taco> getTacos(TacoRepository tacoRepo) {
        List<Taco> tacoList = new ArrayList<>();
        tacoRepo.findAll().forEach(tacoList::add);
        return tacoList;
    }

    private static User getUser(UserRepository userRepo) {
        return userRepo.findByUsername("user");
    }
}
