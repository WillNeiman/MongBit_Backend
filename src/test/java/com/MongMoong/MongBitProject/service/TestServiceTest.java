package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.TestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestServiceTest {

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



    com.MongMoong.MongBitProject.model.Test test = com.MongMoong.MongBitProject.model.Test
            .builder()
            .id("3L")
            .content("Test Content")
            .questions(List.of(question1,question2))
            .results(List.of(testResult1, testResult2))
            .createDate(LocalDateTime.now())
            .imageUrl("test 대표 이미지")
            .playCount(3)
            .build();




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
    @Test
    @DisplayName("test 잘 가져오나 테스트")
    void getTest() {

    }

    @Test
    void getQuestions() {
    }

    @Test
    void getTestResult() {
    }
}