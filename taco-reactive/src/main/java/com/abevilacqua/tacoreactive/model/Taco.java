package com.abevilacqua.tacoreactive.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Flux;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Taco {

    @Id
    private Long id;

    private String name;

    private Date createdAt;

    private Flux<Ingredient> ingredients;

    void createdAt() { this.createdAt = new Date(); }

}
