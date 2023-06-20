package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/random")
    @Operation(summary = "랜덤한 테스트 호출", description = "database에 존재하는 랜덤한 인덱스의 Test를 가져와 반환합니다.")
    public ResponseEntity<Test> getRandomTest() {
        Test randomTest = testService.getRandomTest();
        return ResponseEntity.ok(randomTest);
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<Optional<Test>> getTest(@RequestBody String testId){
        Optional<Test> test = testService.getTest(testId);
        return ResponseEntity.ok(test);
    }

    @GetMapping("")
    public ResponseEntity<List<Test>> getTestList(){
        List<Test> testList = testService.getTestList();
        return ResponseEntity.ok(testList);
    }

    @PutMapping("/test")
    @Operation(summary = "Test 정보 수정", description = "id, title, content, (questions 리스트, results 리스트), imageUrl, playCount 순서로 전달해주세요.")
    public ResponseEntity<Optional<Test>> updateTest(@RequestBody Test test){
        //test 외 다른 model연결된것 아직 업데이트 안됨
        Test updatedTest = testService.updateTest(test);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/test/{testId}")
    @Operation(summary = "Test 삭제", description = "testId가 필요합니다.")
    public ResponseEntity<Void> deleteTest(@PathVariable String testId){
        Test test = new Test();
        test.setId(testId);
        testService.deleteTest(test);
        return ResponseEntity.noContent().build();
    }


}
