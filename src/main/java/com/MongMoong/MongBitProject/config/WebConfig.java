package com.MongMoong.MongBitProject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://mong-bit-frontend.vercel.app",
                        "http://localhost:3000",
                        "http://localhost:8080"
                ) // 리액트 애플리케이션의 URL을 여기에 입력하세요.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.AUTHORIZATION) // 권한 헤더 노출 설정
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addInterceptor는 HandlerInterceptor를 등록할 때 사용한다.
        // HandlerInterceptor는 Spring MVC의 DispatcherServlet이 요청을 처리하는 과정 중에 실행된다.
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns(
                        "/api/1",
                        "/api/2",
                        "/api/3"); // 특정 경로에 대해 인터셉터 실행
    }
}
