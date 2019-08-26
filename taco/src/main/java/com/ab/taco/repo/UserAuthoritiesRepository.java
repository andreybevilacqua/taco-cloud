package com.ab.taco.repo;

import com.ab.taco.model.UserAuthorities;
import org.springframework.data.repository.CrudRepository;

public interface UserAuthoritiesRepository extends CrudRepository<UserAuthorities, Long> {
}
