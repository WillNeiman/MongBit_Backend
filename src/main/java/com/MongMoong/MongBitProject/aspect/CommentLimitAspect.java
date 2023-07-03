package com.MongMoong.MongBitProject.aspect;

import com.MongMoong.MongBitProject.exception.DataMismatchException;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.repository.CommentRepository;
import com.MongMoong.MongBitProject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CommentLimitAspect {

    private final CommentRepository commentRepository;
    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 200;
    @Before("@annotation(com.MongMoong.MongBitProject.aspect.CommentLimitCheck)")
    public void commentLimitCheck(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        dataNullCheck(args);
    }

    private void dataNullCheck(Object[] args){
        Comment comment;
        for (Object arg : args) {
            if (arg instanceof Comment) {
                comment = (Comment) arg;
                if (isCommentNull(comment)) {
                    throw new DataMismatchException("내용이 없습니다.");
                }
                int commentLength = comment.getContent().length();
                if(commentLength<MIN_LENGTH || commentLength > MAX_LENGTH ){
                    throw new DataMismatchException("댓글은 200자까지 작성가능합니다.");
                }
            }
        }

    }
    private boolean isCommentNull(Comment comment) {
        return comment.getContent() == null;
    }
}
