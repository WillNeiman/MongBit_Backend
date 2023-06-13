package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/{memberId}/{testId}")
    public ResponseEntity<Boolean> hasUserLikedTest(@RequestParam String memberId, String testId) {
        boolean hasLiked = likeService.hasUserLikedTest(memberId, testId);
        return ResponseEntity.ok(hasLiked);
    }

    @GetMapping("/count/{testId}")
    public ResponseEntity<Integer> getLikesCountByTestId(@RequestParam String testId) {
        int count = likeService.getLikesCountByTestId(testId);
        return ResponseEntity.ok(count);
    }
}
