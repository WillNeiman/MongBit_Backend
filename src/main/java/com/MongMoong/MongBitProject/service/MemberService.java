package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.config.KakaoOAuth2;
import com.MongMoong.MongBitProject.config.KakaoUserInfo;
import com.MongMoong.MongBitProject.config.TokenProvider;
import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.model.MemberRole;
import com.MongMoong.MongBitProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    public void kakaoLogin(String authorizedCode) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = userInfo.getId();
        String kakaoNickname = userInfo.getNickname();
        String email = userInfo.getEmail();
        String thumbnailImage = userInfo.getThumbnailImage();

        System.out.println("kakaoId = " + kakaoId);
        System.out.println("kakaoNickname = " + kakaoNickname);
        System.out.println("email = " + email);
        System.out.println("thumbnailImage = " + thumbnailImage);

        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;
        System.out.println("password = " + password);

        // DB 에 중복된 Kakao Id 가 있는지 확인
        Member kakaoMember = (Member)memberRepository.findByKakaoId(kakaoId).orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoMember == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            System.out.println("카카오 정보가 DB에 없음, encodedPassword = " + encodedPassword);
            // ROLE = 사용자
            MemberRole role = MemberRole.USER;

            kakaoMember = new Member(kakaoId, kakaoNickname, encodedPassword, email, role);
            memberRepository.save(kakaoMember);
            System.out.println("memberRepository.save(kakaoMember 실행");
        }

        // 로그인 처리
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(kakaoNickname, password, authorities);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // SecurityContextHolder에 저장된 Authentication 객체가 정상적으로 저장되었는지 확인

        /*
        Spring Security의 핵심 클래스 중 하나로, 현재 인증된 사용자의 세부 정보를 저장하는데 사용
        보안 컨텍스트는 사용자의 인증 정보, 그리고 해당 사용자에게 부여된 권한 등의 정보를 담고 있다
        이렇게 저장된 Authentication 객체는 다음과 같은 상황에서 사용된다.
        1. 인증 후 사용자의 세부 정보 필요 시
        - securityContextHolder.getContext().getAuthentication()를 호출, Authentication 객체를
        가져오고 여기서 사용자의 세부 정보를 얻을 수 있다.
        2. 권한 검사
        - @PreAuthorize, @PostAuthorize, @Secured 등의 어노테이션을 통해 메소드 레벨의 보안을 지원
        3. Spring Security의 필터에서 사용
        - 사용자의 요청이 특정 리소스에 접근하기에 충분한 권한을 가지고 있는지를 확인하는 역할을 하는
        FilterSecurityInterceptor 등이 SecurityContextHolder를 사용
         */

        // JWT 토큰 생성
        String jwtToken = tokenProvider.createToken(authentication);

        // JWT 토큰을 사용하여 인증 정보 저장
        // HttpServletRequest나 HttpServletResponse 객체에 접근할 수 없으므로, SecurityContextHolder를 사용
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), jwtToken, authentication.getAuthorities()));
        System.out.println("jwtToken = " + jwtToken);
        SecurityContextHolder.setContext(context);
    }
}
