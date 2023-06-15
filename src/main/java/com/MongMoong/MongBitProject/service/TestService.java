package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    /*
    PageRequest는 Pageable 인터페이스를 구현하는 클래스이므로 Pageable 타입을 요구하는 메소드에 PageRequest 인스턴스를 전달할 수 있다.
    getRecentTests())에서 PageRequest 인스턴스를 생성하고 findByOrderByCreateDateDesc())에 전달하면 타입 오류가 발생하지 않는다.
     */

    // 새로운 테스트 생성
    public Test createTest(Test test) {
        Test createdTest = testRepository.save(test);
        return createdTest;
    }
    public List<Test> getRecentTests(int size) {
        Page<Test> page = testRepository.findByOrderByCreateDateDesc(PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createDate")));
        return page.getContent();
    }

//    public Optional<Test> getTest(String id){
//        Optional<Test> test = testRepository.findById(id);
//        test.ifPresent(t -> {
//            List<Question> questions = testRepository.findQuestionById(id);
//            List<TestResult> testResults = testRepository.findTestResultById(id);
//            t.setQuestions(questions);
//            t.setResults(testResults);
//        });
//        return test;
//    }
    public List<Question> getQuestions(String id){
        return testRepository.findQuestionsById(id);
    }
    public List<TestResult> getTestResult(String id){
        return testRepository.findTestResultsById(id);
    }


}
