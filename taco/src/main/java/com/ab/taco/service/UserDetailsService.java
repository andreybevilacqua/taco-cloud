package com.ab.taco.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserDetailsService {

    Optional<UserDetails> loadUserByUsername (String username);
}
