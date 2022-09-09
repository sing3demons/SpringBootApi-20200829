package com.sing3demons.springbootapi.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sing3demons.springbootapi.entity.User;

@Service
public class TokenService {
    @Value("${app.token.secret}")
    private String jwtSecret;

    @Value("${app.token.issuer}")
    private String issuer;

    public String tokenize(User user) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        Date expiresAt = calendar.getTime();
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getId())
                .withClaim("role", "USER")
                .withExpiresAt(expiresAt)
                .sign(algorithm());
    }

    public DecodedJWT verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm())
                    .withIssuer(issuer).build();

            return verifier.verify(token);

        } catch (Exception e) {
            return null;
        }

    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }
}
