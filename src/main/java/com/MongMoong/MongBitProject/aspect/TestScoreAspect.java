package com.MongMoong.MongBitProject.aspect;

import com.MongMoong.MongBitProject.exception.DataMismatchException;
import com.MongMoong.MongBitProject.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TestScoreAspect {

    @Before("@annotation(com.MongMoong.MongBitProject.aspect.TestScoreCheck)")
    public void checkScoreValidate(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        int[] score = scoreIntArrAndLengthCheck(args);
        if(score.length != 4 || score ==null){
            throw new DataMismatchException("score가 유효하지 않습니다.");
        }
    }

    private int[] scoreIntArrAndLengthCheck(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof int[]) {
                return (int[]) arg;
            } else if (arg instanceof String[]) {
                String[] scoreStrArr = (String[]) arg;
                int[] scoreIntArr = new int[scoreStrArr.length];
                try {
                    for (int i = 0; i < scoreStrArr.length; i++) {
                        scoreIntArr[i] = Integer.parseInt(scoreStrArr[i]);
                    }
                    return scoreIntArr;
                } catch (NumberFormatException e) {
                    throw new DataMismatchException("score가 유효하지 않습니다.");
                }
            }
        }
        return null;
    }
}