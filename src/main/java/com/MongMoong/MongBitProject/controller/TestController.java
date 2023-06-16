package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tests")
public class TestController {
    private final TestService testService;

    @PostMapping("/test")
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        Test createdTest = testService.createTest(test);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @GetMapping("/detail/{test_id}")
//    public ResponseEntity<Test> getTest(@PathVariable String id) {
//        Optional<Test> test = testService.getTest(id);
//        if (test.isPresent()) {
//            return ResponseEntity.ok(test.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/quizzes/{question_id}")
    public ResponseEntity<List<Question>> getQuestion(@PathVariable String id){
        List<Question> question = testService.getQuestions(id);
        return ResponseEntity.ok(question);
    }
//    @GetMapping("/quizzes/{test_id}/result/{result_id}")
//    public ResponseEntity<TestResult> getTestResult(@PathVariable String id, String result){
//    Optional<TestResult> testResult = testService.getTestResult(id,result);
//        if (testResult.isPresent()) {
//            return ResponseEntity.ok(testResult.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


}
