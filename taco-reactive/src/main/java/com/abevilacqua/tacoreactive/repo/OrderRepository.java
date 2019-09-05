package com.abevilacqua.tacoreactive.repo;

import com.abevilacqua.tacoreactive.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {

}
