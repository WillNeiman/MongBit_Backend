package com.MongMoong.MongBitProject.aspect;

import com.MongMoong.MongBitProject.config.TokenProvider;
import com.MongMoong.MongBitProject.exception.TokenVerificationException;
import com.MongMoong.MongBitProject.exception.UnauthorizedException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
public class AdminCheckAspect {

    private final TokenProvider tokenProvider;

    @Before("@annotation(com.MongMoong.MongBitProject.aspect.AdminRequired)")
    public void checkAdmin(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String token = tokenProvider.resolveToken(request);
        tokenProvider.validateTokenAndCheckAdmin(token);
        System.out.println("관리자 권한 확인");
    }
}
