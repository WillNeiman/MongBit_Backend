package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.config.TokenProvider;
import com.MongMoong.MongBitProject.dto.KakaoLoginResponse;
import com.MongMoong.MongBitProject.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2/kakao")
@Tag(name = "OAuth Controller", description = "OAuth2 로그인 및 JWT 발급과 관련된 API를 제공하는 컨트롤러입니다.")
public class OAuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @Value("${kakao.oauth.url}")
    private String kakaoOAuthUrl;

    /*
    8100포트 테스트용 url
    https://kauth.kakao.com/oauth/authorize?client_id=3245a5f9cb8303814aadbe1eb65b2e73&redirect_uri=http://localhost:8100/login/oauth2/kakao/code&response_type=code
    배포 테스트용 url
    https://kauth.kakao.com/oauth/authorize?client_id=3245a5f9cb8303814aadbe1eb65b2e73&redirect_uri=https://mongbit-willneiman.koyeb.app/login/oauth2/kakao/code&response_type=code
    프론트엔드 도메인
    https://mongbit-frontend-moorisong.koyeb.app/
    http://localhost:8100/api/v1/test/649a7bccaa04db61384808c5/648153acc5f9f12b045730de/like
     */

    // 카카오 OAuth 인증 URL을 반환하는 엔드포인트. 테스트용
    @GetMapping("/url")
    @Operation(summary = "카카오 OAuth 인증 URL을 반환")
    public ResponseEntity<String> getKakaoOAuthUrl() {
        return ResponseEntity.ok(kakaoOAuthUrl);
    }

    @GetMapping("/code")
    @Operation(summary = "OAuth 기반 카카오 회원가입/로그인 처리 후 회원정보와 jwt를 반환",
            description = "카카오 인가 코드 code와 Referer 정보가 담긴 request가 각각 필요합니다.")
    public ResponseEntity<KakaoLoginResponse> kakaoLogin(String code, HttpServletRequest request) {
        // 요청 도메인 가져오기
        String url = request.getHeader("Referer");
        System.out.println("호출 도메인: " + url);
        KakaoLoginResponse kakaoLoginResponse = memberService.kakaoLogin(code, url);

        // JWT 토큰 가져오기
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = (String) currentAuthentication.getCredentials();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("(String)principal = " + (String) principal);
        System.out.println("jwtToken = " + jwtToken);

        // JWT 토큰을 HTTP 응답에 포함시키기, 바디에 썸네일과 가입일 정보 담아서 보내기
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .body(kakaoLoginResponse);
    }

}
