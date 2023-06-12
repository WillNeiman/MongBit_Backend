package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NewQuizController {

    private final QuizService quizService;
    public NewQuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    //테스트 생성을 위한 제목, 질문 갯수 입력
    @PostMapping("/quizzes")
    public String newQuiz(@RequestParam("title") String title, @RequestParam("questionCount") int questionCount) {
        return "redirect:/quizzes/" + title + "/" + questionCount;
    }

    //테스트 질문, 결과 입력
    @GetMapping("/quizzes/{title}/{questionCount}")
    public String createQuiz(@PathVariable("title") String title, @PathVariable("questionCount") String questionCount) {
        int count = Integer.parseInt(questionCount);

        if (count % 2==0){
            count -= 1;
        } else if(count>9){
            count = 9;
        } else if (count<3) {
            count = 3;
        }

        for(int i=0;i<count;i++){
            //테스트 질문 입력 받기
        }
        return "result";
    }

    //테스트 질문 수정
    @PatchMapping("/quizzes/{test_id}/question/{question_id}")
    public String updateQuiz(@PathVariable("test_id") String testId, @PathVariable("question_id") String questionId) {
        // 질문 업데이트 로직 구현

        return "result";
    }
    
    //테스트 결과 수정
    @PatchMapping("/quizzes/{test_id}/result/{result_id}")
    public String updateTestResult(@PathVariable("test_id") String testId, @PathVariable("result_id") String resultId) {
        // 테스트 결과 업데이트 로직 구현

        return "result";
    }
    
    //테스트 삭제
    @DeleteMapping("/quizzes/{test_id}")
    public String deleteQuiz(@PathVariable("test_id") String testId) {
        //퀴즈 삭제 로직
        return "result";
    }

}
