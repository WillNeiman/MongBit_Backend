package com.MongMoong.MongBitProject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Test
    void deleteQuestion() {
        questionService.deleteQuestion("question1");
        System.out.println("---------------------success");
    }
}