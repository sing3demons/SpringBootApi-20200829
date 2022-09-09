package com.sing3demons.springbootapi.config.token;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sing3demons.springbootapi.service.TokenService;

public class TokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenService service;

    public TokenFilterConfigurer(TokenService service) {
        this.service = service;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        TokenFilter filter = new TokenFilter(service);
        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

}
