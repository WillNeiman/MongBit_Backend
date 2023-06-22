package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.config.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/tokens")
@RequiredArgsConstructor
public class MemberController {

    // TODO 마이페이지 진입 시 토큰 유효성 검사
    private final TokenProvider tokenProvider;

    @GetMapping("/validity")
    @Operation(summary = "토큰 유효성 검사", description = "헤더에 jwt를 담아 보내주세요.")
    public ResponseEntity<String> jwtValidate() {
        String message = "jwt 검증 성공";
        return ResponseEntity.ok(message);
    }

}
