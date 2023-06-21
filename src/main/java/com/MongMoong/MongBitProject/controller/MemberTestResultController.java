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

    @GetMapping("/{kakaoId}")
    public ResponseEntity<?> getResultsByMemberId(
            @PathVariable String kakaoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MemberTestResult> results = memberTestResultService.getResultsByMemberId(Long.parseLong(kakaoId), page, size);
        return ResponseEntity.ok(results);
    }
    @PostMapping("/{testId}/{memberId}")
    @Operation()
    public ResponseEntity<Optional<TestResult>> updateMemberTestResult(
            @PathVariable String testId,
            @PathVariable String memberId,
            @RequestBody Map<String, int[]> request) {
        int[] score = request.get("score");
        MemberTestResult createMemberTestResult = memberTestResultService.createMemberTestResult(testId, memberId, score);
        Optional<TestResult> testResult = testResultService.getTestResult(createMemberTestResult.getTestResultId());
        return ResponseEntity.ok(testResult);
    }
}
