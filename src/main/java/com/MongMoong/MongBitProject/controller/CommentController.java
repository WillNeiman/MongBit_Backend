package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.dto.CommentResponse;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{testId}/comment")
    public ResponseEntity<Comment> createComment(@PathVariable String testId, @RequestBody Comment comment) {
        Comment savedComment = commentService.saveComment(testId, comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @PutMapping("/{testId}/comment")
    public ResponseEntity<Comment> updateComment(@PathVariable String testId, @RequestBody Comment comment) {
        commentService.updateComment(testId, comment);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{testId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String testId, @RequestBody Comment comment) {
        commentService.deleteComment(testId, comment);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{testId}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentList(@PathVariable String testId) {
        List<CommentResponse> commentResponses = commentService.getCommentsForTest(testId);
        return ResponseEntity.ok(commentResponses);
    }
}
