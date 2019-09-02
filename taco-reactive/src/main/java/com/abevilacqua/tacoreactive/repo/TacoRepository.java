package com.abevilacqua.tacoreactive.repo;

import com.abevilacqua.tacoreactive.model.Taco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {

}
