package com.MongMoong.MongBitProject.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
커스텀 인증 성공 핸들러. 사용자의 인증이 성공했을 때 호출되며,
JWT 토큰을 생성하여 응답에 담아 클라이언트에게 전달.
 */
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    public JwtAuthenticationSuccessHandler(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    // OAuth2 인증 절차를 직접 구현했기 때문에 사실 안씀
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        /*
        HttpServletRequest: 클라이언트의 HTTP 요청 정보를 담고 있는 객체
        HttpServletResponse: 서버의 HTTP 응답을 생성하기 위한 객체
        Authentication: 사용자의 인증 정보를 담고 있는 객체
         */
        String token = tokenProvider.createToken(authentication);
        System.out.println("successHandler(jwtAuthenticationSuccessHandler) = " + token);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("{\"access_token\":\"" + token + "\"}");
    }
}
