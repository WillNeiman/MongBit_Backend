package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.dto.RecentTestResponse;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.CommentRepository;
import com.MongMoong.MongBitProject.repository.LikeRepository;
import com.MongMoong.MongBitProject.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final QuestionService questionService;
    private final TestResultService testResultService;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    /*
    PageRequest는 Pageable 인터페이스를 구현하는 클래스이므로 Pageable 타입을 요구하는 메소드에 PageRequest 인스턴스를 전달할 수 있다.
    getRecentTests())에서 PageRequest 인스턴스를 생성하고 findByOrderByCreateDateDesc())에 전달하면 타입 오류가 발생하지 않는다.
     */

    // 새로운 테스트 생성
//    public Test createTest(Test test) {
//        test.setCreateDate(LocalDateTime.now());
//        Test createdTest = testRepository.save(test);
//        return createdTest;
//    }
    public Test createTest(Test test) {
        test.setCreateDate(LocalDateTime.now());
        test.setPlayCount(0);
        List<Question> questionList = test.getQuestions();
        questionService.createQuestionList(questionList);
        List<TestResult> testResultList = test.getResults();
        testResultService.createTestResultList(testResultList);
        Test createdTest = testRepository.save(test);
        return createdTest;
    }
    //최근 테스트 순서로 테스트 불러오기
    public List<Test> getRecentTests(int size) {
        Page<Test> page = testRepository.findByOrderByCreateDateDesc(PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createDate")));
        return page.getContent();
    }
    public List<RecentTestResponse> getRecentTests(int page, int size) {
        Page<Test> recentTestPage = testRepository.findByOrderByCreateDateDesc(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));
        List<Test> recentTestList = recentTestPage.getContent();
        List<RecentTestResponse> recentTestResponseList = new ArrayList<>();
        for (Test test : recentTestList) {
            RecentTestResponse recentTestResponse = new RecentTestResponse(test.getId(), test.getTitle(), test.getImageUrl(), test.getPlayCount());
            int likeCount = likeRepository.countByTestId(test.getId());
            recentTestResponse.setLikeCount(likeCount);
            int commentCount = commentRepository.countByTestId(test.getId());
            recentTestResponse.setCommentCount(commentCount);
            recentTestResponseList.add(recentTestResponse);
        }
        return recentTestResponseList;
    }
    //랜덤 테스트 불러오기
    public Test getRandomTest(){
        long count = testRepository.count();
        int random = (int)(Math.random() * count);
        Page<Test> page = testRepository.findAll(PageRequest.of(random, 1, Sort.unsorted()));
        return page.getContent().get(0);
    }
    //모든 테스트 불러오기(리스트)
    public List<Test> getTestList(){
        List<Test> testList = testRepository.findAll();
        return testList;
    }
    //특정 테스트 하나 불러오기
    public Optional<Test> getTest(String id){
        Optional<Test> test = testRepository.findById(id);
        test.ifPresent(t -> {
            t.setContent(HtmlUtils.htmlEscape(t.getContent()).replaceAll("\n", "<br>"));
        });
        return test;
    }
    //테스트 수정
    public Test updateTest(Test updatedTest) {
        Optional<Test> optionalTest = testRepository.findById(updatedTest.getId());
        if (optionalTest.isPresent()) {
//            questionService.updateQuestionList(updatedTest.getQuestions());
            testResultService.updateTestResultList(updatedTest.getResults());
            return testRepository.save(updatedTest);
        } else {
            throw new IllegalArgumentException(updatedTest.getId()+" not exit");
        }
    }
    //테스트 삭제
    public void deleteTest(Test test){
        Optional<Test> deletedTest = testRepository.findById(test.getId());
        List<Question> questionList = deletedTest.get().getQuestions();
        System.out.println(questionList);
        for (Question question : questionList) {
            questionService.deleteQuestion(question.getId());

        }
        List<TestResult> testResultList = deletedTest.get().getResults();
        for (TestResult testResult : testResultList) {
            testResultService.deleteTestResult(testResult.getId());
        }
        testRepository.delete(test);
    }

}

