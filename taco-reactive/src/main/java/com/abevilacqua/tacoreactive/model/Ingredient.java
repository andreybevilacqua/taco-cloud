package com.abevilacqua.tacoreactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Document
public class Ingredient {

  @Id
  private String id;
  @NotNull
  @Size(min = 5, message = "Name must be at least 5 characters long")
  private final String name;
  private final Type type;

  public enum Type {
      WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }
}

