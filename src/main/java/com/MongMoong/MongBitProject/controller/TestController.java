package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.dto.TestCoverResponse;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.service.TestResultService;
import com.MongMoong.MongBitProject.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tests")
public class TestController {
    private final TestService testService;
    private final TestResultService testResultService;


    @PostMapping("/test")
    @Operation(summary = "테스트 만들기",
            description = "필요한 데이터:" +
                    "Test의 title, content, questions(Question_id 리스트),  results(TestResult_id 리스트), imageUrl" +
                    "Question의 index, question, answerPlus, answerMinus " +
                    "TestResult의 result(MBTI 16가지), title, content, imageUrl")
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        Test createdTest = testService.createTest(test);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/random")
    @Operation(summary = "랜덤한 테스트 호출", description = "database에 존재하는 랜덤한 인덱스의 Test를 가져와 반환합니다.")
    public ResponseEntity<Test> getRandomTest() {
        Test randomTest = testService.getRandomTest();
        return ResponseEntity.ok(randomTest);
    }

    @GetMapping("/test/{testId}")
    @Operation(summary = "특정 테스트 조회", description = "테스트 하나에 대한 내용을 가져와 반환합니다.")
    public ResponseEntity<Test> getTest(@PathVariable String testId){
        Test findTest = testService.getTest(testId);
        System.out.println("testId = " + testId);
        return ResponseEntity.ok(findTest);
    }

    @GetMapping("")
    @Operation(summary = "모든 테스트 조회", description = "모든 테스트를 리스트로 가져와 반환합니다.")
    public ResponseEntity<List<TestCoverResponse>> getTestList(){
        List<TestCoverResponse> testList = testService.getTestList();
        return ResponseEntity.ok(testList);
    }

    @PatchMapping("/test")
    @Operation(summary = "Test 정보 수정", description = "title, content, questions 리스트, results 리스트, imageUrl, playCount, id 순서로 전달해주세요.")
    public ResponseEntity<Test> updateTest(@RequestBody Test test){
        Test updatedTest = testService.updateTest(test);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/test/{testId}")
    @Operation(summary = "Test 삭제", description = "testId가 필요합니다. question, testResult가 같이 삭제됩니다.")
    public ResponseEntity<Void> deleteTest(@PathVariable String testId){
        testService.deleteTest(testId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{page}/{size}")
    @Operation(summary = "최신 테스트 불러오기", description = "0부터 시작하는 page와 size가 필요합니다.")
    public ResponseEntity<List<TestCoverResponse>> getRecentTest(@PathVariable int page, @PathVariable int size) {
        List<TestCoverResponse> recentTest = testService.getRecentTests(page, size);
        return ResponseEntity.ok(recentTest);
    }

}
