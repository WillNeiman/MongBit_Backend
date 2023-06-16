package com.MongMoong.MongBitProject.aspect;

import com.MongMoong.MongBitProject.exception.ResourceNotFoundException;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TestExistenceAtLikeAspect {

    private final TestRepository testRepository;

    @Before("@annotation(com.MongMoong.MongBitProject.aspect.TestExistenceAtLikeCheck)")
    public void checkTestExistence(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String testId = findTestId(args);
        Test test = testRepository.findById(testId).orElseThrow(() -> new ResourceNotFoundException("해당 테스트가 조회되지 않았습니다. " + testId));
    }

    private String findTestId(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof String) {
                return (String)arg;
            }
        }
        throw new ResourceNotFoundException("testId를 찾을 수 없습니다.");
    }
}
