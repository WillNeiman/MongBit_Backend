package com.MongMoong.MongBitProject.config;

import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberRepository memberRepository;
    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        return authentication -> {
            String memberId = authentication.getPrincipal().toString();
            String password = authentication.getCredentials().toString();

            // 사용자 정보를 데이터베이스에서 조회
            Member member = memberRepository.findById(memberId).orElseThrow(
                    () -> new UsernameNotFoundException("User not found: " + memberId)
            );

            // 비밀번호를 확인
            if (!passwordEncoder.matches(password, member.getPassword())) {
                // 비밀번호가 일치하지 않으면 인증 실패
                throw new BadCredentialsException("Invalid username or password");
            }

            // 인증 성공: 새 UsernamePasswordAuthenticationToken 반환
            return new UsernamePasswordAuthenticationToken(memberId, password, member.getAuthorities());
        };
    }


    // OAuth2 인증 절차를 직접 구현했기 때문에 사실 안씀
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
            .authorizeRequests(authorize ->
                    authorize.anyRequest().permitAll()
            )
            .oauth2Login()
                .userInfoEndpoint()
                .and()
                .successHandler(jwtAuthenticationSuccessHandler);

        return http.build();
    }

}