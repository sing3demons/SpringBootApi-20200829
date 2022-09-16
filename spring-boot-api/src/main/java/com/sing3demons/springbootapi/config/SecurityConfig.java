package com.sing3demons.springbootapi.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sing3demons.springbootapi.config.token.TokenFilterConfigurer;
import com.sing3demons.springbootapi.service.TokenService;

@Configuration
public class SecurityConfig {
    private final TokenService filter;

    public SecurityConfig(TokenService filter) {
        this.filter = filter;
    }

    private final String[] authorizedUrl = {
            "/actuator/**",
            "/api/auth/register",
            "/api/auth/login",
            "/socket/**",
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(config -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowCredentials(true);
            cors.setAllowedOriginPatterns(Collections.singletonList("http://*"));
            cors.addAllowedHeader("*");
            cors.addAllowedMethod(HttpMethod.OPTIONS);
            cors.addAllowedMethod(HttpMethod.POST);
            cors.addAllowedMethod(HttpMethod.GET);
            cors.addAllowedMethod(HttpMethod.PUT);
            cors.addAllowedMethod(HttpMethod.DELETE);

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", cors);

            config.configurationSource(source);
        }).csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(authorizedUrl)
                .anonymous().anyRequest().authenticated()
                .and().apply(new TokenFilterConfigurer(filter));
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/js/**", "/images/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

}
