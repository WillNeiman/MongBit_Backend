package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.TestRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestServiceTest {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestService testService;

    private com.MongMoong.MongBitProject.model.Test test;
    private Question question;
    private TestResult testResult;

    @BeforeEach
    void setUp() {

        Question question1 = Question.builder()
                .id("question1-아이디")
                .index(1)
                .property("I")
                .question("Question 1 내용")
                .build();

        Question question2 = Question.builder()
                .id("question2-아이디")
                .index(2)
                .property("J")
                .question("Question 2 내용")
                .build();

        TestResult testResult1 = TestResult.builder()
                .id("testResult1-id아이디")
                .result("ENFP")
                .title("Test Result 1 제목")
                .content("Test Result Content 1 내용")
                .imageUrl("testResult1-image-url 이미지")
                .build();

        TestResult testResult2 = TestResult.builder()
                .id("testResult2-id아이디")
                .result("INFP")
                .title("Test Result 2제목")
                .content("Test Result Content  2 내용")
                .imageUrl("testResult2-image-url 이미지")
                .build();

        test = com.MongMoong.MongBitProject.model.Test
                .builder()
                .id("3L")
                .content("Test Content")
                .questions(List.of(question1,question2))
                .results(List.of(testResult1, testResult2))
                .createDate(LocalDateTime.now())
                .imageUrl("test 대표 이미지")
                .playCount(3)
                .build();

        testRepository.save(test);
    }
    @AfterEach
    void tearDown(){
        testRepository.delete(this.test);
    }

    @Test
    @DisplayName(">>>test get section")
    void getTest() {
        // given
        String testId = "3L";

        // when
        com.MongMoong.MongBitProject.model.Test result = testService.getTest(testId).orElse(null);

        // then
        Assertions.assertThat(result).isInstanceOf(com.MongMoong.MongBitProject.model.Test.class);
        Assertions.assertThat("Test Content").isEqualTo(result.getContent());
        Assertions.assertThat("test 대표 이미지").isEqualTo(result.getImageUrl());
        System.out.println("콘솔확인: "+result.toString());
        //Assertions.assertThat("I").isEqualTo(result.getQuestions().get(0).getProperty());
    }

    @Test
    @DisplayName(">>>question get section")
    void getQuestions() {
        // given
        String testId = "3L";

        // when
        List<Question> questions = testService.getQuestions(testId);

        // then
        Assertions.assertThat(questions).contains(question);
    }

    @Test
    @DisplayName(">>>test result get section")
    void getTestResults() {
        // given
        String testId = "3L";
        String resultValue = "ENFP";

        // when
        Optional<TestResult> results = testService.getTestResult(testId, resultValue);

        // then
        assertTrue(results.isPresent());
        assertEquals(resultValue, results.get().getResult());
    }



    /*
            // Save the Test object to the database
        testRepository.save(test);

        // Get the Test object from the database
        Test savedTest = testRepository.findById(test.getId()).orElse(null);

        // Assertions
        assertEquals(test.getId(), savedTest.getId());
        assertEquals(test.getTitle(), savedTest.getTitle());
        assertEquals(test.getContent(), savedTest.getContent());
        assertEquals(test.getQuestions().size(), savedTest.getQuestions().size());
        assertEquals(test.getResults().size(), savedTest.getResults().size());
        assertEquals(test.getCreateDate(), savedTest.getCreateDate());
        assertEquals(test.getImageUrl(), savedTest.getImageUrl());
        assertEquals(test.getPlayCount(), savedTest.getPlayCount());
     */


}