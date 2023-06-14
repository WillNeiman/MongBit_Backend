package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.exception.ResourceNotFoundException;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.repository.CommentRepository;
import com.MongMoong.MongBitProject.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TestRepository testRepository;

    public Comment saveComment(String testId, Comment comment) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new ResourceNotFoundException("해당 테스트가 조회되지 않았습니다. " + testId));
        comment.setTestId(testId);
        comment.setCommentDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}
