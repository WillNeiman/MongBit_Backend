package com.MongMoong.MongBitProject.config;

import com.MongMoong.MongBitProject.exception.TokenVerificationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final long TOKEN_VALIDITY = 1000 * 60 * 60; // 1 hour
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // 사용자 인증 정보를 기반으로 JWT 토큰을 생성
    // 인증된 사용자의 권한 정보를 ','로 연결한 문자열로 변환하여 토큰의 권한 정보 claim에 설정
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

    // HTTP 요청 헤더에서 "Authorization" 헤더 값을 추출
    // 토큰은 "Bearer " 이후의 문자열로 이루어져 있으므로, "Bearer "을 제외한 실제 토큰 값만 추출
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        } throw new TokenVerificationException("토큰이 존재하지 않습니다.");
    }

    // 주어진 토큰의 유효성을 검증
    // JWT 라이브러리를 사용하여 시크릿 키로 토큰의 서명을 검증
    // 만약 검증에 실패하면 JWTDecodeException을 던지므로, 이를 catch하여 false 반환
    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
            return true;
        } catch (JWTDecodeException ex) {
            throw new JWTDecodeException("토큰 형식이 잘못되었습니다.");
        } catch (TokenExpiredException ex) {
            throw new TokenExpiredException("토큰이 만료되었습니다.");
        } catch (SignatureVerificationException ex) {
            throw new TokenVerificationException("토큰 시그니처 검증에 실패했습니다.");
        }
    }

    // 주어진 토큰의 유효성을 검증 및 어드민 권한 체크
    public boolean validateTokenAndCheckAdmin(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
            String[] roles = jwt.getClaim(AUTHORITIES_KEY).asString().split(",");
            for (String role : roles) {
                System.out.println(role);
            }
            if (Arrays.stream(roles).noneMatch(role -> role.equals("ROLE_ADMIN"))) {
                throw new TokenVerificationException("권한이 없습니다.");
            }

            return true;
        } catch (JWTDecodeException ex) {
            throw new JWTDecodeException("토큰 형식이 잘못되었습니다.");
        } catch (TokenExpiredException ex) {
            throw new TokenExpiredException("토큰이 만료되었습니다.");
        } catch (SignatureVerificationException ex) {
            throw new TokenVerificationException("토큰 시그니처 검증에 실패했습니다.");
        }
    }

    // 주어진 토큰으로부터 memberId 추출
    public String getPrincipalFromToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        return jwt.getSubject();
    }


    // 주어진 토큰으로부터 사용자 인증 정보를 추출
    // 토큰으로부터 사용자 이름(subject)와 권한 정보를 추출하여 Authentication 객체를 생성
    // 만약 토큰이 유효하지 않거나 사용자 이름 정보가 없는 경우, null 반환
    @Nullable
    public Authentication getAuthentication(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        String user = jwt.getSubject();

        String[] roles = jwt.getClaim(AUTHORITIES_KEY).asString().split(",");

        return new UsernamePasswordAuthenticationToken(user, "",
                Arrays.stream(roles)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }

}
