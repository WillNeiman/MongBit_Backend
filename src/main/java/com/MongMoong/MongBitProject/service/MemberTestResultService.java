package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.MemberTestResult;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.MemberTestResultRepository;
import com.MongMoong.MongBitProject.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberTestResultService {

    /*
score[0] > 0 == "E" else "I"
score[1] > 0 == "N" else "S"
score[2] > 0 == "F" else "T"
score[3] > 0 == "J" else "P"
 */
    private final MemberTestResultRepository memberTestResultRepository;
    private final TestRepository testRepository;
    private final TestService testService;

    public Page<MemberTestResult> getResultsByMemberId(Long kakaoId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "testDate"));
        return memberTestResultRepository.findByMemberId(kakaoId, pageable);
    }

    public MemberTestResult createMemberTestResult(String testId, String memberId, int[] score) {
        String result = setResult(score);
        MemberTestResult memberTestResult = new MemberTestResult();
        Test findTest = testService.getTest(testId);
        List<TestResult> testTestList = findTest.getResults();
        for (TestResult testResult : testTestList) {
            if (testResult.getResult().equals(result)) {
                memberTestResult.setTestResultId(testResult.getId());
            }
        }
        memberTestResult.setTestId(testId);
        memberTestResult.setMemberId(memberId);
        memberTestResult.setTestDate(LocalDateTime.now());
        Test test = testService.getTest(testId);
        test.setPlayCount(test.getPlayCount() + 1);
        testService.updateTest(test);
        memberTestResultRepository.save(memberTestResult);
        return memberTestResult;
    }

    private static String setResult(int[] score) {
        String result = "";
        if (score[0] > 0) {
            result += "E";
        } else {
            result += "I";
        }
        if (score[1] > 0) {
            result += "N";
        } else {
            result += "S";
        }
        if (score[2] > 0) {
            result += "F";
        } else {
            result += "T";
        }
        if (score[3] > 0) {
            result += "J";
        } else {
            result += "P";
        }
        return result;
    }
}
