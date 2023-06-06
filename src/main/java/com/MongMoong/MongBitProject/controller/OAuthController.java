package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.config.KakaoOAuth2;
import com.MongMoong.MongBitProject.config.KakaoUserInfo;
import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final MemberService memberService;
    private final KakaoOAuth2 kakaoOAuth2;

    @GetMapping("/login/oauth2/kakao/code")
    public ResponseEntity<String> kakaoLogin(String code, HttpServletRequest request) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        System.out.println("code = " + code);

        memberService.kakaoLogin(code);
        System.out.println("kakaoLogin() 완료");

        // JWT 토큰 가져오기
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("현재 인증된 사용자: " + currentAuthentication);
        String jwtToken = (String) currentAuthentication.getCredentials();

        // JWT 토큰을 HTTP 응답에 포함시키기
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .body("New jwt response");
    }

}
