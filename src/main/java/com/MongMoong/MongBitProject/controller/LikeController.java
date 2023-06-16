package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.model.Like;
import com.MongMoong.MongBitProject.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/{testId}/{memberId}/like")
    @Operation(summary = "특정 테스트에 대해 로그인한 사용자의 좋아요 여부 확인", description = "testId와 memberId가 필요합니다.")
    public ResponseEntity<Boolean> hasUserLikedTest(@PathVariable String memberId, @PathVariable String testId) {
        boolean hasLiked = likeService.hasUserLikedTest(memberId, testId);
        return ResponseEntity.ok(hasLiked);
    }

    @GetMapping("/{testId}/like/count")
    @Operation(summary = "특정 테스트에 대한 좋아요 수 조회", description = "testId가 필요합니다.")
    public ResponseEntity<Integer> getLikesCountByTestId(@PathVariable String testId) {
        int count = likeService.getLikesCountByTestId(testId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/{testId}/{memberId}/like")
    @Operation(summary = "특정 테스트에 대해 로그인한 사용자의 좋아요 생성", description = "testId와 memberId가 필요합니다.")
    public ResponseEntity createLike(@PathVariable String memberId, @PathVariable String testId) {
        Like likeResponse = likeService.createLike(memberId, testId);
        return ResponseEntity.status(HttpStatus.CREATED).body(likeResponse);
    }
    @DeleteMapping("/{testId}/{memberId}/like")
    @Operation(summary = "특정 테스트에 대해 로그인한 사용자의 좋아요 삭제", description = "testId와 memberId가 필요합니다.")
    public ResponseEntity deleteLike(@PathVariable String memberId, @PathVariable String testId) {
        likeService.deleteLike(memberId, testId);
        return ResponseEntity.noContent().build();
    }
}
