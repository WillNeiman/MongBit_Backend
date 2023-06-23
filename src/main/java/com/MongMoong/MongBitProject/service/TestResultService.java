package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.dto.TestResultResponse;
import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.QuestionRepository;
import com.MongMoong.MongBitProject.repository.TestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestResultService {
    private final TestResultRepository testResultRepository;


    public List<TestResult> createTestResultList(List<TestResult> testResultLists){
        return testResultRepository.saveAll(testResultLists);
    }

    public TestResult createTestResult(TestResult testResult){
        return testResultRepository.save(testResult);
    }
    public List<TestResult> getTestResultList(){
        return testResultRepository.findAll();
    }

    public TestResultResponse getTestResult(String id){
        TestResult testResult = testResultRepository.findById(id).get();
        TestResultResponse testResultResponse = new TestResultResponse(testResult.getResult(), testResult.getTitle(), testResult.getContent(), testResult.getImageUrl());
        return testResultResponse;
    }

    public TestResult updateTestResult(TestResult testResult){
        return testResultRepository.save(testResult);
    }
    public List<TestResult> updateTestResultList(List<TestResult> testResultList){
        return testResultRepository.saveAll(testResultList);
    }
    public void deleteTestResult(String id){
        testResultRepository.deleteById(id);
    }

}
