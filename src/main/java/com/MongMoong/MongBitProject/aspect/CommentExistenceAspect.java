package com.MongMoong.MongBitProject.aspect;

import com.MongMoong.MongBitProject.exception.ResourceNotFoundException;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CommentExistenceAspect {

    private final CommentRepository commentRepository;

    @Before("@annotation(com.MongMoong.MongBitProject.aspect.CommentExistenceCheck)")
    public void checkCommentExistence(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String commentId = findCommentId(args);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 댓글이 조회되지 않았습니다. " + commentId));
    }

    private String findCommentId(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Comment) {
                Comment comment = (Comment) arg;
                return comment.getId();
            }
        }
        throw new ResourceNotFoundException("Comment의 id를 찾을 수 없습니다.");
    }
}
