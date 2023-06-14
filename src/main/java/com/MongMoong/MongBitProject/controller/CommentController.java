package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{testId}")
    public ResponseEntity<Comment> createComment(@PathVariable String testId, @RequestBody Comment comment) {
        Comment savedComment = commentService.saveComment(testId, comment);
        return ResponseEntity.ok(savedComment);
    }
}
