package com.abevilacqua.tacoreactive.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Taco {

  @Id
  private String id;
  @NotNull
  @Size(min = 5, message = "Name must be at least 5 characters long")
  private String name;
  private LocalDate createdAt;

  @Size(min = 1, message = "You must choose at least 1 ingredient")
  private Flux<Ingredient> ingredients;
}
