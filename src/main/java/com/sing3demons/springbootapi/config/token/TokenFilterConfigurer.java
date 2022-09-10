package com.sing3demons.springbootapi.config.token;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sing3demons.springbootapi.service.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenService service;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        TokenFilter filter = new TokenFilter(service);
        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
