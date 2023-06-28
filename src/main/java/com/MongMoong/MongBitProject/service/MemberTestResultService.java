package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.aspect.MemberExistenceAtTestCheck;
import com.MongMoong.MongBitProject.aspect.TestExistenceCheck;
import com.MongMoong.MongBitProject.aspect.TestScoreCheck;
import com.MongMoong.MongBitProject.dto.MemberTestResultDTO;
import com.MongMoong.MongBitProject.dto.MemberTestResultResponse;
import com.MongMoong.MongBitProject.model.MemberTestResult;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.MemberTestResultRepository;
import com.MongMoong.MongBitProject.repository.TestRepository;
import com.MongMoong.MongBitProject.repository.TestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private final TestResultRepository testResultRepository;
    private final TestService testService;

    // 최근 테스트 결과 페이지로 불러오기
    public MemberTestResultResponse<MemberTestResultDTO> getResultsByMemberId(String memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "testDate"));
        Page<MemberTestResult> memberTestResultPage = memberTestResultRepository.findByMemberId(memberId, pageable);
        List<MemberTestResult> memberTestResultList = memberTestResultPage.getContent();

        List<MemberTestResultDTO> memberTestResultDTOList = new ArrayList<>();

        for(MemberTestResult memberTestResult : memberTestResultList) {
            String testId = memberTestResult.getTestId();
            LocalDateTime testDate = memberTestResult.getTestDate();
            String testResultId = memberTestResult.getTestResultId();

            TestResult testResult = testResultRepository.findById(testResultId).orElseThrow();
            String title = testResult.getTitle();
            String content = testResult.getContent();

            Test test = testRepository.findById(testId).orElseThrow();
            String imageUrl = test.getImageUrl();

            MemberTestResultDTO memberTestResultDTO = new MemberTestResultDTO(testId, title, content, imageUrl, testDate, testResultId);
            memberTestResultDTOList.add(memberTestResultDTO);
        }

        MemberTestResultResponse<MemberTestResultDTO> memberTestResultResponse = new MemberTestResultResponse<>();
        memberTestResultResponse.setMemberTestResultDTOList(memberTestResultDTOList);
        memberTestResultResponse.setHasNextPage(memberTestResultPage.hasNext());

        return memberTestResultResponse;
    }

    // 회원의 테스트 결과 저장하기
    @MemberExistenceAtTestCheck
    @TestExistenceCheck
    @TestScoreCheck
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

    // 테스트 결과 점수를 MBTI로 변환하기
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
