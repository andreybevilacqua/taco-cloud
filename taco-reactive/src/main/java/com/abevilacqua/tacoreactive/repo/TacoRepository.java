package com.abevilacqua.tacoreactive.repo;

import com.abevilacqua.tacoreactive.model.Taco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {

}
