package com.MongMoong.MongBitProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.addAllowedOrigin("https://mongbit.vercel.app");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("https://mongbit-willneiman.koyeb.app");

        config.addAllowedHeader("*");

        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        config.addExposedHeader(HttpHeaders.AUTHORIZATION);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
