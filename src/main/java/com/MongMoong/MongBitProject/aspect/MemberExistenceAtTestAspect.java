package com.MongMoong.MongBitProject.aspect;

import com.MongMoong.MongBitProject.exception.ResourceNotFoundException;
import com.MongMoong.MongBitProject.exception.UnauthorizedException;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class MemberExistenceAtTestAspect {

    private final MemberRepository memberRepository;

    @Before("@annotation(com.MongMoong.MongBitProject.aspect.MemberExistenceAtTestCheck)")
    public void checkMemberExistence(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String memberId = findMemberIdFromArgs(args);
        System.out.println("memberId = " + memberId);

        // SecurityContext에서 사용자 정보를 가져온다
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof String)) {
            throw new IllegalArgumentException("잘못된 사용자 정보입니다.");
        }

        String username = (String)principal;
        System.err.println("username = " + username);

        // 비로그인 상태일 경우
        if (username.equals("anonymousUser")) {
            throw new UnauthorizedException("로그인하지 않은 사용자의 요청입니다.");
        }

        // username과 memberId를 비교
        if (!username.equals(memberId)) {
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
