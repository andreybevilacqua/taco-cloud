package com.ab.taco.controller;

import com.ab.taco.model.RegistrationForm;
import com.ab.taco.repo.UserAuthoritiesRepository;
import com.ab.taco.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final UserRepository repository;

    private final UserAuthoritiesRepository authoritiesRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public RegistrationController(PasswordEncoder encoder, UserAuthoritiesRepository authoritiesRepository, UserRepository repository) {
        this.encoder = encoder;
        this.authoritiesRepository = authoritiesRepository;
        this.repository = repository;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public ResponseEntity processRegistration(RegistrationForm form) {
        repository.save(form.toUser(encoder));
        authoritiesRepository.save(form.toAuthority(form.getUsername(), "USER"));
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
