package com.MongMoong.MongBitProject.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final long TOKEN_VALIDITY = 1000 * 60 * 60; // 1 hour
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + TOKEN_VALIDITY);

        return JWT.create()
                .withSubject(authentication.getName())
                .withExpiresAt(validity)
                .withClaim(AUTHORITIES_KEY, authorities)
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public Authentication getAuthentication(String token) {
        String user = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();

        if (user != null) {
            String[] roles = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token)
                    .getClaim(AUTHORITIES_KEY)
                    .asString()
                    .split(",");
            return new UsernamePasswordAuthenticationToken(user, "",
                    Arrays.stream(roles)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()));
        }
        return null;
    }
}
