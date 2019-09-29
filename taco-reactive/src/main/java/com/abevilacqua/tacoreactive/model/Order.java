package com.abevilacqua.tacoreactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Document
@RequiredArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String id;

  private LocalDate placedAt;

  private List<Taco> tacos;

  public void addTaco(Taco taco) {
    this.tacos.add(taco);
  }
}
