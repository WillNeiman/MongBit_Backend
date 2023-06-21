package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.MemberTestResult;
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

    public Page<MemberTestResult> getResultsByMemberId(Long kakaoId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "testDate"));
        return memberTestResultRepository.findByMemberId(kakaoId, pageable);
    }

    public MemberTestResult createMemberTestResult(String testId, String memberId, int[] score){
        String result = "";
        setResult(score, result);
        //TODO 계산된 result를 바탕으로 해당 Test의 TestResult를 찾은 후 그 id를 찾아서 MemberTestResult에 set
        MemberTestResult memberTestResult = new MemberTestResult();
        List<TestResult> testTestList = testRepository.findById(testId).get().getResults();
        for (TestResult testResult : testTestList) {
            if(testResult.getResult().equals(result)){
                memberTestResult.setTestResultId(testResult.getId());
            }
        }
        memberTestResult.setTestId(testId);
        memberTestResult.setMemberId(memberId);
        memberTestResult.setTestDate(LocalDateTime.now());
        return memberTestResultRepository.save(memberTestResult);
    }

    private static void setResult(int[] score, String result) {
        for (int i = 0; i < 4; i++) {
            if(i == 0 && score[i] > 0) {
                result += "E";
            } else {
                result += "I";
            }
            if(i == 1 && score[i] > 0) {
                result += "N";
            } else {
                result += "S";
            }
            if(i == 2 && score[i] > 0) {
                result += "F";
            } else {
                result += "T";
            }
            if(i == 3 && score[i] > 0) {
                result += "J";
            } else {
                result += "P";
            }
        }
    }
}
