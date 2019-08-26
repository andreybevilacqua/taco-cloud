package com.ab.taco.service.util;

import com.ab.taco.model.Ingredient;
import com.ab.taco.model.Order;
import com.ab.taco.model.Taco;
import com.ab.taco.model.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ab.taco.model.Ingredient.Type.PROTEIN;
import static com.ab.taco.model.Ingredient.Type.WRAP;

public final class ObjectCreator {

    public static Order buildOrder() {
        List<Ingredient> ingredients = Stream.of(
                new Ingredient( "Flour Tortilla", WRAP),
                new Ingredient( "Corn Tortilla", WRAP),
                new Ingredient( "Ground Beef", PROTEIN),
                new Ingredient( "Carnitas", PROTEIN)).collect(Collectors.toList());

        List<Taco> tacos = Collections.singletonList(Taco.builder().id(1L).name("Taco 1").ingredients(ingredients).build());
        User user = new User("user",
                "user",
                "User",
                "my_street",
                "my_city",
                "my_state",
                "123",
                "123",
                true);

        return Order.builder()
                .id(1L)
                .placedAt(new Date())
                .name("Order 1")
                .street("Street 1")
                .city("City 1")
                .state("State 1")
                .zip("Zip 1")
                .ccNumber("4111111111111111")
                .ccCVV("123")
                .ccExpiration("01/20")
                .tacos(tacos)
                .user(user)
                .build();
    }
}
