package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.model.MemberTestResult;
import com.MongMoong.MongBitProject.service.MemberTestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member-test-result")
public class MemberTestResultController {

    private final MemberTestResultService memberTestResultService;

    @GetMapping("/{kakaoId}")
    public ResponseEntity<Page<MemberTestResult>> getResultsByMemberId(
            @PathVariable String kakaoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MemberTestResult> results = memberTestResultService.getResultsByMemberId(Long.parseLong(kakaoId), page, size);
        return ResponseEntity.ok(results);
    }
}
