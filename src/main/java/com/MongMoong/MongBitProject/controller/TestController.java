package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.dto.TestDTO;
import com.MongMoong.MongBitProject.model.Answer;
import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.service.AnswerService;
import com.MongMoong.MongBitProject.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tests")
public class TestController {
    private final TestService testService;
    private final AnswerService answerService;

    @PostMapping("/test")
    @Operation(summary = "테스트 만들기", description = "test, question, testResult 모든 필드가 있어야 합니다.")
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        Test createdTest = testService.createTest(test);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @PostMapping("/test")
//    public ResponseEntity<Test> createTest(@RequestBody TestDTO testDTO) {
////        Test test = testDTO.getTest();
//                Test test = new Test();
//        test.setTitle(testDTO.getTitle());
//        test.setContent(testDTO.getContent());
//        test.setImageUrl(testDTO.getImageUrl());
////        List<Question> questionList = testDTO.getQuestionList();
////        List<Answer> answerList = testDTO.getAnswerList();
//        List<TestResult> testResultList = testDTO.getTestResultList();
////        // 질문에 대한 답변 생성
////        for (Question question : questionList) {
////            List<Answer> answers = question.getAnswers();
////            List<Answer> createdAnswers = answerService.createAnswerList(answers);
////            question.setAnswers(createdAnswers);
////        }
//
//        System.out.println("---------------------------16 end.");
//        testService.createTest(test, null, testResultList);
//        System.out.println("---------------------------17 end.");
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @GetMapping("/random")
    @Operation(summary = "랜덤한 테스트 호출", description = "database에 존재하는 랜덤한 인덱스의 Test를 가져와 반환합니다.")
    public ResponseEntity<Test> getRandomTest() {
        Test randomTest = testService.getRandomTest();
        return ResponseEntity.ok(randomTest);
    }

    @GetMapping("/test/{testId}")
    @Operation(summary = "특정 테스트 조회", description = "테스트 하나에 대한 내용을 가져와 반환합니다.")
    public ResponseEntity<Optional<Test>> getTest(@PathVariable String testId){
        Optional<Test> test = testService.getTest(testId);
        return ResponseEntity.ok(test);
    }

    @GetMapping("")
    @Operation(summary = "모든 테스트 조회", description = "모든 테스트를 리스트로 가져와 반환합니다.")
    public ResponseEntity<List<Test>> getTestList(){
        List<Test> testList = testService.getTestList();
        return ResponseEntity.ok(testList);
    }

    @PatchMapping("/test")
    @Operation(summary = "Test 정보 수정(answer에 업데이트 적용 미완)", description = "title, content, questions 리스트, results 리스트, imageUrl, playCount, id 순서로 전달해주세요.")
    public ResponseEntity<Optional<Test>> updateTest(@RequestBody Test test){
        //test 외 다른 model연결된것 아직 업데이트 안됨
        Test updatedTest = testService.updateTest(test);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/test/{testId}")
    @Operation(summary = "Test 삭제(answer에 삭제 적용 미완)", description = "testId가 필요합니다. question, testResult가 같이 삭제됩니다.")
    public ResponseEntity<Void> deleteTest(@PathVariable String testId){
        Test test = new Test();
        test.setId(testId);
        testService.deleteTest(test);
        return ResponseEntity.noContent().build();
    }


}
