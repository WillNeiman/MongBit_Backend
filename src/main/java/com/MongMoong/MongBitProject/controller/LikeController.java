package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.model.Like;
import com.MongMoong.MongBitProject.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
@Tag(name = "Like Controller", description = "좋아요와 관련된 API를 제공하는 컨트롤러입니다.")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/{testId}/{memberId}/like")
    @Operation(summary = "특정 테스트에 대해 로그인한 사용자의 좋아요 여부 확인", description = "testId와 memberId가 필요합니다. 파라미터의 순서를 엄수해주세요.")
    public ResponseEntity<Boolean> hasUserLikedTest(@PathVariable String testId, @PathVariable String memberId) {
        boolean hasLiked = likeService.hasUserLikedTest(testId, memberId);
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
    public ResponseEntity createLike(@PathVariable String testId, @PathVariable String memberId) {
        Like likeResponse = likeService.createLike(testId, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(likeResponse);
    }
    @DeleteMapping("/{testId}/{memberId}/like")
    @Operation(summary = "특정 테스트에 대해 로그인한 사용자의 좋아요 삭제", description = "testId와 memberId가 필요합니다.")
    public ResponseEntity deleteLike(@PathVariable String testId, @PathVariable String memberId) {
        likeService.deleteLike(testId, memberId);
        return ResponseEntity.noContent().build();
    }
}
