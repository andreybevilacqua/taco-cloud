package com.ab.taco.service.Impl;

import com.ab.taco.repo.UserRepository;
import com.ab.taco.service.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserDetails> loadUserByUsername(String username) {
        return Optional.ofNullable(repository.findByUsername(username));
    }
}
