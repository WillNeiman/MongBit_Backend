package com.MongMoong.MongBitProject.controller;

import com.MongMoong.MongBitProject.dto.CommentDTO;
import com.MongMoong.MongBitProject.dto.CommentResponse;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.service.CommentService;
import com.MongMoong.MongBitProject.service.MemberService;
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
    private final MemberService memberService;

    @PostMapping("/comment")
    @Operation(summary = "특정 테스트에 대한 새로운 댓글 생성", description = "Comment의 memberId, testId, content가 필요합니다.")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment savedComment = commentService.saveComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @PutMapping("/comment")
    @Operation(summary = "특정 테스트에 대한 댓글 업데이트", description = "업데이트할 Comment의 memberId, testId, content가 필요합니다.")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment) {
        commentService.updateComment(comment);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comment/{commentId}")
    @Operation(summary = "특정 테스트에 대한 댓글 삭제", description = "삭제할 Comment의 id가 필요합니다")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
        Comment comment = new Comment();
        comment.setId(commentId);
        commentService.deleteComment(comment);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comments/{testId}")
    @Operation(summary = "특정 테스트에 대한 모든 댓글 조회", description = "testId가 필요합니다")
    public ResponseEntity<List<CommentDTO>> getCommentList(@PathVariable String testId) {
        Comment comment = new Comment();
        comment.setTestId(testId);
        List<CommentDTO> commentRespons = commentService.getCommentsForTest(comment);
        return ResponseEntity.ok(commentRespons);
    }

    @GetMapping("/comments/{testId}/page/{pageNumber}")
    @Operation(summary = "특정 테스트에 대한 댓글 조회 (페이지당 10개)", description = "testId와 pageNumber가 필요합니다.")
    public ResponseEntity<CommentResponse<CommentDTO>> getCommentListPaged(@PathVariable String testId, @PathVariable int pageNumber) {
        Comment comment = new Comment();
        comment.setTestId(testId);
        CommentResponse<CommentDTO> commentResponse = commentService.getCommentsForTestPaged(comment, pageNumber);
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/{testId}/comments/count")
    @Operation(summary = "특정 테스트에 대한 댓글 수 조회", description = "testId가 필요합니다.")
    public ResponseEntity<Integer> getCommentsCountByTestId(@PathVariable String testId) {
        Comment comment = new Comment();
        comment.setTestId(testId);
        int count = commentService.getCommentsCountByTestId(comment);
        return ResponseEntity.ok(count);
    }

}
