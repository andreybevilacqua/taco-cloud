package com.abevilacqua.tacoreactive.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) {
    http.authorizeRequests()
        .antMatchers("/**").permitAll();
  }
}
