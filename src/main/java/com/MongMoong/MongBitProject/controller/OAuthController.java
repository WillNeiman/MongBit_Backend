package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.config.KakaoOAuth2;
import com.MongMoong.MongBitProject.config.KakaoUserInfo;
import com.MongMoong.MongBitProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final MemberService memberService;
    private final KakaoOAuth2 kakaoOAuth2;
    @Value("${kakao.oauth.url}")
    private String kakaoOAuthUrl;

    /*
    로컬 테스트용 url
    https://kauth.kakao.com/oauth/authorize?client_id=3245a5f9cb8303814aadbe1eb65b2e73&redirect_uri=http://localhost:8080/login/oauth2/kakao/code&response_type=code
    배포 테스트용 url
    https://kauth.kakao.com/oauth/authorize?client_id=3245a5f9cb8303814aadbe1eb65b2e73&redirect_uri=https://mongbit-willneiman.koyeb.app/login/oauth2/kakao/code&response_type=code
    프론트엔드 도메인
    https://mongbit-frontend-moorisong.koyeb.app/
     */

    // 카카오 OAuth 인증 URL을 반환하는 엔드포인트
    @GetMapping("/login/oauth2/kakao/url")
    public ResponseEntity<String> getKakaoOAuthUrl() {
        // 카카오 OAuth 인증 URL 반환, 테스트 환경에 따라 URL을 변경해야 할 수 있음
        System.out.println("kakaoOAuthUrl 전달 = " + kakaoOAuthUrl);
        return ResponseEntity.ok(kakaoOAuthUrl);
    }

    @GetMapping("/login/oauth2/kakao/code")
    public ResponseEntity<String> kakaoLogin(String code, HttpServletRequest request, HttpSession session) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        System.out.println("code = " + code);

        KakaoUserInfo userInfo = memberService.kakaoLogin(code);
        System.out.println("kakaoLogin() 완료");

        // 세션에 프로필 이미지 저장하기
        session.setAttribute("thumbnailImage", userInfo.getThumbnailImage());

        // JWT 토큰 가져오기
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("현재 인증된 사용자: " + currentAuthentication);
        String jwtToken = (String) currentAuthentication.getCredentials();

        // JWT 토큰을 HTTP 응답에 포함시키기
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .body("Bearer " + jwtToken);
    }

}
