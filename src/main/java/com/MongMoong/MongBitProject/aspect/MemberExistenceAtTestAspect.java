package com.MongMoong.MongBitProject.aspect;

import com.MongMoong.MongBitProject.exception.ResourceNotFoundException;
import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class MemberExistenceAtTestAspect {

    private final MemberRepository memberRepository;

    @Before("@annotation(com.MongMoong.MongBitProject.aspect.MemberExistenceAtTestCheck)")
    public void checkTestExistence(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String memberId = findMemberId(args);
        System.out.println("memberId = " + memberId);
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 회원입니다 " + memberId));
    }

    private String findMemberId(Object[] args) {
        if (args[1] instanceof String) {
            return (String) args[1];
        }
        throw new ResourceNotFoundException("memberId를 찾을 수 없습니다.");
    }
}
