package com.abevilacqua.tacoreactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Builder
@Document
public class Taco {

  @Id
  private final String id;
  @NotNull
  @Size(min = 5, message = "Name must be at least 5 characters long")
  private final String name;
  private final LocalDate createdAt;

  @Size(min = 1, message = "You must choose at least 1 ingredient")
  private Flux<Ingredient> ingredients;
}
