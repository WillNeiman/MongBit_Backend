package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.config.TokenProvider;
import com.MongMoong.MongBitProject.model.MemberTestResult;
import com.MongMoong.MongBitProject.service.MemberTestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member-test-result")
public class MemberTestResultController {

    private final TokenProvider tokenProvider;

    private final MemberTestResultService memberTestResultService;

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
    public ResponseEntity<MemberTestResult> updateMemberTestResult(
            @PathVariable String testId,
            @PathVariable String memberId,
            @RequestBody Map<String, int[]> request) {
        int[] score = request.get("score");
        MemberTestResult createMemberTestResult = memberTestResultService.createMemberTestResult(testId, memberId, score);
        return ResponseEntity.ok(createMemberTestResult);
    }
}
