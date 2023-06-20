package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.QuestionRepository;
import com.MongMoong.MongBitProject.repository.TestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<TestResult> getTestResult(String id){
        return testResultRepository.findById(id);
    }

    public TestResult updateTestResult(TestResult testResult){
        return testResultRepository.save(testResult);
    }
    public void deleteTestResult(String id){
        testResultRepository.deleteById(id);
    }

}