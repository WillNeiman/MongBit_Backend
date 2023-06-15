package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.dto.CommentResponse;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "특정 테스트에 대한 새로운 댓글 생성", description = "testId와 댓글의 세부사항이 필요합니다")
    public ResponseEntity<Comment> createComment(@PathVariable String testId, @RequestBody Comment comment) {
        Comment savedComment = commentService.saveComment(testId, comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @PutMapping("/{testId}/comment")
    @Operation(summary = "특정 테스트에 대한 댓글 업데이트", description = "testId와 업데이트할 댓글의 세부사항이 필요합니다")
    public ResponseEntity<Comment> updateComment(@PathVariable String testId, @RequestBody Comment comment) {
        commentService.updateComment(testId, comment);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{testId}/comment/{commentId}")
    @Operation(summary = "특정 테스트에 대한 댓글 삭제", description = "testId와 댓글의 ID가 필요합니다")
    public ResponseEntity<Void> deleteComment(@PathVariable String testId, @RequestBody Comment comment) {
        commentService.deleteComment(testId, comment);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{testId}/comments")
    @Operation(summary = "특정 테스트에 대한 모든 댓글 조회", description = "testId가 필요합니다")
    public ResponseEntity<List<CommentResponse>> getCommentList(@PathVariable String testId) {
        List<CommentResponse> commentResponses = commentService.getCommentsForTest(testId);
        return ResponseEntity.ok(commentResponses);
    }
}
