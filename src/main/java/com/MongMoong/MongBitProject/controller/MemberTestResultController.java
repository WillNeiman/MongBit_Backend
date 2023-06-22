package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.config.TokenProvider;
import com.MongMoong.MongBitProject.model.MemberTestResult;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.service.MemberTestResultService;
import com.MongMoong.MongBitProject.service.TestResultService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member-test-result")
public class MemberTestResultController {

    private final TokenProvider tokenProvider;

    private final MemberTestResultService memberTestResultService;
    private final TestResultService testResultService;

    @GetMapping("/{memberId}")
    @Operation(summary = "회원별 10개씩 테스트 결과 조회", description = "memberId, page, size가 필요합니다.")
    public ResponseEntity<?> getResultsByMemberId(
            @PathVariable String kakaoId,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size
    ) {
        Page<MemberTestResult> results = memberTestResultService.getResultsByMemberId(Long.parseLong(kakaoId), page, size);
        return ResponseEntity.ok(results);
    }
    @PostMapping("/{testId}/{memberId}")
    @Operation(summary = "테스트 결과를 회원에 저장" , description = "testId, memberId 순으로 전달해주세요. score는 길이가 4인 정수배열입니다.")
    public ResponseEntity<TestResult> updateMemberTestResult(
            @PathVariable String testId,
            @PathVariable String memberId,
            @RequestBody Map<String, int[]> request) {
        int[] score = request.get("score");
        MemberTestResult createMemberTestResult = memberTestResultService.createMemberTestResult(testId, memberId, score);
        TestResult testResult = testResultService.getTestResult(createMemberTestResult.getTestResultId());
        return ResponseEntity.ok(testResult);
    }
}
