package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/{memberId}/{testId}")
    public ResponseEntity<Boolean> hasUserLikedTest(@PathVariable String memberId, String testId) {
        boolean hasLiked = likeService.hasUserLikedTest(memberId, testId);
        return ResponseEntity.ok(hasLiked);
    }

    @GetMapping("/count/{testId}")
    public ResponseEntity<Integer> getLikesCountByTestId(@PathVariable String testId) {
        int count = likeService.getLikesCountByTestId(testId);
        return ResponseEntity.ok(count);
    }
}
