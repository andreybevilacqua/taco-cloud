package com.abevilacqua.tacokitchen.model;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class Ingredient {

    private Long id;
    private final String name;
    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
