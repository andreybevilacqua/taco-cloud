package com.ab.taco.repo;

import com.ab.taco.model.Taco;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {

    Iterable<Taco> findAll(Pageable page);

}
