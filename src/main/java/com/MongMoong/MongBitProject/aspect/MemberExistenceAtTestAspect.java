package com.MongMoong.MongBitProject.aspect;

import com.MongMoong.MongBitProject.config.TokenProvider;
import com.MongMoong.MongBitProject.exception.ResourceNotFoundException;
import com.MongMoong.MongBitProject.exception.UnauthorizedException;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.repository.MemberRepository;
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
public class MemberExistenceAtTestAspect {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Before("@annotation(com.MongMoong.MongBitProject.aspect.MemberExistenceAtTestCheck)")
    public void checkMemberExistence(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String memberId = findMemberIdFromArgs(args);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = tokenProvider.resolveToken(request);
        tokenProvider.validateToken(token);
        String username = tokenProvider.getPrincipalFromToken(token);

        // 비로그인 상태일 경우
        if (username.equals("anonymousUser") || username == null) {
            throw new UnauthorizedException("로그인하지 않은 사용자의 요청입니다.");
        }

        boolean isAdmin = tokenProvider.isAdminFromToken(token);

        // username과 memberId를 비교, 단 권한이 ROLE_ADMIN인 경우는 비교 생략
        if (!isAdmin && !username.equals(memberId)) {
            throw new UnauthorizedException("로그인 사용자와 요청 사용자가 일치하지 않습니다.");
        }

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 회원입니다 " + memberId));
    }
    private String findMemberIdFromArgs(Object[] args) {
        if (args[0] instanceof Comment) {
            return ((Comment) args[0]).getMemberId();
        } else if (args[1] instanceof String) {
            return (String) args[1];
        }
        throw new ResourceNotFoundException("memberId를 찾을 수 없습니다.");
    }


}
