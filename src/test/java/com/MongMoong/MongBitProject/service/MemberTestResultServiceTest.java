package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.MemberTestResult;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.MemberTestResultRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberTestResultServiceTest {
    @Autowired
    private MemberTestResultService memberTestResultService;

    @Test
    public void testCreateMemberTestResult() {

        // 가상의 입력 데이터
        String testId = "6492ac69ccc0ac2642eb53ea";
        String memberId = "648153acc5f9f12b045730de";
        int[] score = {1, -1, 1, -1};

        // 가상의 테스트 결과
        com.MongMoong.MongBitProject.model.Test findTest = new com.MongMoong.MongBitProject.model.Test();
        TestResult testResult = new TestResult();
        testResult.setId("testResultId");
        List<TestResult> testResultList = new ArrayList<>();
        testResultList.add(testResult);
        findTest.setResults(testResultList);

        // 테스트 실행
        MemberTestResult result = memberTestResultService.createMemberTestResult(testId, memberId, score);

        // 결과 확인
        Assertions.assertNotNull(result);
        // 이하 원하는 단언문 추가
    }

}