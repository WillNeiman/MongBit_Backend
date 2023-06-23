package com.MongMoong.MongBitProject.aspect;

import com.MongMoong.MongBitProject.exception.DataMismatchException;
import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.Test;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TestNullAspect {

    @Before("@annotation(com.MongMoong.MongBitProject.aspect.TestNullCheck)")
    public void testNullCheck(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        dataNullCheck(args);
    }

    private void dataNullCheck(Object[] args) {
        Test test;
        for (Object arg : args) {
            if (arg instanceof Test) {
                test = (Test) arg;
                if (isTestDataIncomplete(test)) {
                    throw new DataMismatchException("모든 데이터가 채워져야 합니다.");
                }
                if(test.getQuestions().size() != 12){
                    throw new DataMismatchException("question은 12개를 등록해야 합니다.");
                }
//                for (int i = 0; i < test.getQuestions().size(); i++) {
//                    Question question = test.getQuestions().get(i);
//                    if (question.getIndex() != i) {
//                        throw new DataMismatchException("question index를 확인해주세요.");
//                    }
//                }


                if(test.getResults().size() != 16){
                    throw new DataMismatchException("testResult는 16개를 등록해야 합니다.");
                }
            }
        }


    }
    private boolean isTestDataIncomplete(Test test) {
        return test.getTitle() == null || test.getContent() == null || test.getQuestions() == null || test.getResults() == null || test.getImageUrl() == null;
    }
}
