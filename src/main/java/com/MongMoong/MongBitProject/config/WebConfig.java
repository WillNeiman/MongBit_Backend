package com.MongMoong.MongBitProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://mongbit-frontend-moorisong.koyeb.app", "http://localhost:3000") // 리액트 애플리케이션의 URL을 여기에 입력하세요.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.AUTHORIZATION) // 권한 헤더 노출 설정
                .allowCredentials(true);
    }
}
