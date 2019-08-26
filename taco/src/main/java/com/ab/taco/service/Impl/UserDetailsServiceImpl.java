package com.ab.taco.service.Impl;

import com.ab.taco.repo.UserRepository;
import com.ab.taco.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public Optional<UserDetails> loadUserByUsername(String username) {
        return Optional.ofNullable(repository.findByUsername(username));
    }
}
