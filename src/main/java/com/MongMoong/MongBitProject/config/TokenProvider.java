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
    private static final long TOKEN_VALIDITY = 1000 * 60 * 30; //* 60; // 1 hour
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

    // JWT 토큰을 검증하여 사용자를 인증하는 메소드
    // 인증에 성공하면 Authentication 객체를 반환하고, 실패하면 null을 반환
    public Authentication getAuthentication(String token) {
        // 주어진 시크릿 키(SECRET_KEY)를 사용하여 JWT 검증을 위한 인스턴스를 생성
        String user = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                //JWT 검증 인스턴스를 빌드하여 검증 설정을 완료
                .build()
                // 토큰(token)을 검증
                .verify(token)
                // 검증된 토큰에서 사용자 정보(subject)를 추출, 사용자 정보는 토큰을 생성할 때 설정된 값.
                .getSubject();

        // 사용자 정보가 존재하는 경우
        if (user != null) {
            // 토큰을 검증하여 권한 정보를 추출
            //주어진 시크릿 키(SECRET_KEY)를 사용하여 JWT 검증을 위한 인스턴스를 생성
            String[] roles = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token)
                    // 검증된 토큰에서 권한 정보(claim)를 추출, 권한 정보는 토큰을 생성할 때 설정된 값.
                    .getClaim(AUTHORITIES_KEY)
                    // 권한 정보를 문자열 형태로 변환
                    .asString()
                    // 문자열 형태의 권한 정보를 ','를 기준으로 분할하여 배열로 변환
                    .split(",");
            // 추출된 사용자 정보와 권한 정보를 사용하여 UsernamePasswordAuthenticationToken 객체를 생성하여 반환
            return new UsernamePasswordAuthenticationToken(user, "",
                    // 권한 배열을 스트림으로 변환하고,
                    Arrays.stream(roles)
                            // 각 권한을 SimpleGrantedAuthority 객체로 매핑한 후,
                            .map(SimpleGrantedAuthority::new)
                            // 리스트로 수집.
                            .collect(Collectors.toList()));
        }
        // 사용자 정보가 존재하지 않는 경우 null을 반환
        return null;
    }
}
