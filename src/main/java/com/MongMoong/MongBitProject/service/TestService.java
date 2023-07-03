package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.aspect.TestExistenceCheck;
import com.MongMoong.MongBitProject.aspect.TestNullCheck;
import com.MongMoong.MongBitProject.dto.TestCoverDTO;
import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.dto.TestCoverResponse;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.CommentRepository;
import com.MongMoong.MongBitProject.repository.LikeRepository;
import com.MongMoong.MongBitProject.repository.MemberTestResultRepository;
import com.MongMoong.MongBitProject.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final QuestionService questionService;
    private final TestResultService testResultService;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final MemberTestResultRepository memberTestResultRepository;
    private final CacheManager cacheManager;

    /*
    PageRequest는 Pageable 인터페이스를 구현하는 클래스이므로 Pageable 타입을 요구하는 메소드에 PageRequest 인스턴스를 전달할 수 있다.
    getRecentTests())에서 PageRequest 인스턴스를 생성하고 findByOrderByCreateDateDesc())에 전달하면 타입 오류가 발생하지 않는다.
     */

    // 테스트 생성
    @CacheEvict(value = {"recentTests", "testList"}, allEntries = true) // 기존의 최신 테스트 캐싱 데이터를 무효화함
    @TestNullCheck
    public Test createTest(Test test) {
        test.setCreateDate(LocalDateTime.now());
        test.setPlayCount(0);
        test.setContent(test.getContent().replaceAll("\n", "<br>"));
        List<Question> questionList = test.getQuestions();
        questionService.createQuestionList(questionList);
        List<TestResult> testResultList = test.getResults();
        for (TestResult testResult : testResultList) {
            testResult.setContent(testResult.getContent().replaceAll("\n", "<br>"));
        }
        testResultService.createTestResultList(testResultList);
        Test createdTest = testRepository.save(test);

        return createdTest;
    }

    //테스트 수정
    @CacheEvict(value = {"recentTests", "testList"}, allEntries = true)
    @TestExistenceCheck
    @TestNullCheck
    public Test updateTest(Test test) {
        test.setContent(test.getContent().replaceAll("\n", "<br>"));
        questionService.updateQuestionList(test.getQuestions());
        for (TestResult testResult : test.getResults()) {
            testResult.setContent(testResult.getContent().replaceAll("\n", "<br>"));
        }
        testResultService.updateTestResultList(test.getResults());
        return testRepository.save(test);
    }

    //테스트 삭제
    @CacheEvict(value = {"recentTests", "testList"}, allEntries = true)
    @TestExistenceCheck
    public void deleteTest(String testId){
        Test findTest = testRepository.findById(testId).get();
        List<Question> questionList = findTest.getQuestions();
        for (Question question : questionList) {
            questionService.deleteQuestion(question.getId());
        }
        List<TestResult> testResultList = findTest.getResults();
        for (TestResult testResult : testResultList) {
            testResultService.deleteTestResult(testResult.getId());
        }
//        List<MemberTestResult> memberTestResultList = memberTestResultService.getResultsByTestId(testId);
//        for (MemberTestResult memberTestResult : memberTestResultList) {
//            memberTestResultService.de
//        }
        memberTestResultRepository.deleteAllByTestId(testId);
        testRepository.delete(findTest);
    }

    // 최신 테스트 페이징해서 불러오기
    @Cacheable(value = "recentTests", key = "#page + '-' + #size")
    public TestCoverResponse<TestCoverDTO> getRecentTests(int page, int size) {
        System.out.println("getRecentTests() 디비에 조회 쿼리 날림. 캐싱 시작.");
        System.out.println("Fetching from DB for getRecentTests(), page / size" + page + " / " + size); // 로깅 추가
        Page<Test> recentTestPage = testRepository.findByOrderByCreateDateDesc(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate")));
        List<Test> recentTestList = recentTestPage.getContent();
        List<TestCoverDTO> testCoverDTOList = new ArrayList<>();
        for (Test test : recentTestList) {
            TestCoverDTO testCoverDTO = new TestCoverDTO(test.getId(), test.getTitle(), test.getImageUrl(), test.getPlayCount());
            int likeCount = likeRepository.countByTestId(test.getId());
            testCoverDTO.setLikeCount(likeCount);
            int commentCount = commentRepository.countByTestId(test.getId());
            testCoverDTO.setCommentCount(commentCount);
            testCoverDTOList.add(testCoverDTO);
        }

        TestCoverResponse<TestCoverDTO> testCoverResponse = new TestCoverResponse<>();
        testCoverResponse.setTestCoverDTOList(testCoverDTOList);
        testCoverResponse.setHasNextPage(recentTestPage.hasNext());
        return testCoverResponse;
    }

    //모든 테스트 불러오기(리스트)
    @Cacheable(value = "testList")
    public List<TestCoverDTO> getTestList(){
        System.out.println("getTestList() 디비에 조회 쿼리 날림. 캐싱 시작.");
        List<Test> testList = testRepository.findAll();
        List<TestCoverDTO> testCoverDTOList = new ArrayList<>();
        for (Test test : testList) {
            TestCoverDTO testCoverDTO = new TestCoverDTO(test.getId(), test.getTitle(), test.getImageUrl(), test.getPlayCount());
            int likeCount = likeRepository.countByTestId(test.getId());
            testCoverDTO.setLikeCount(likeCount);
            int commentCount = commentRepository.countByTestId(test.getId());
            testCoverDTO.setCommentCount(commentCount);
            testCoverDTOList.add(testCoverDTO);
        }
        return testCoverDTOList;
    }

    //특정 테스트 하나 불러오기
    @Cacheable(value = "test", key = "#testId")
    @TestExistenceCheck
    public Test getTest(String testId){
        System.out.println("Fetching from DB for testId = " + testId); // 로깅 추가
        Test test = testRepository.findById(testId).get();
        return test;
    }

    //랜덤 테스트 불러오기
    public Test getRandomTest(){
        long count = testRepository.count();
        int random = (int)(Math.random() * count);
        Page<Test> page = testRepository.findAll(PageRequest.of(random, 1, Sort.unsorted()));
        return page.getContent().get(0);
    }
}

