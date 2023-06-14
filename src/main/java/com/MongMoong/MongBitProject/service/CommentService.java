package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.aspect.TestExistenceCheck;
import com.MongMoong.MongBitProject.dto.CommentResponse;
import com.MongMoong.MongBitProject.exception.ResourceNotFoundException;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.repository.CommentRepository;
import com.MongMoong.MongBitProject.repository.MemberRepository;
import com.MongMoong.MongBitProject.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TestRepository testRepository;
    private final MemberRepository memberRepository;

    @TestExistenceCheck
    public Comment saveComment(String testId, Comment comment) {
        comment.setTestId(testId);
        comment.setCommentDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @TestExistenceCheck
    public void updateComment(String testId, Comment comment) {
        commentRepository.save(comment);
    }

    @TestExistenceCheck
    public void deleteComment(String testId, Comment comment) {
        commentRepository.delete(comment);
    }

    @TestExistenceCheck
    public List<CommentResponse> getCommentsForTest(String testId) {
        List<Comment> comments = commentRepository.findByTestId(testId);
        List<String> memberIds = comments.stream().map(Comment::getMemberId).collect(Collectors.toList());
        List<Member> members = memberRepository.findByIdIn(memberIds);
        Map<String, String> memberIdUsernameMap = members.stream().collect(Collectors.toMap(Member::getId, Member::getUsername));
        List<CommentResponse> commentResponses = new ArrayList<>();
        for(Comment comment : comments) {
            String memberId = comment.getMemberId();
            String username = memberIdUsernameMap.get(memberId);
            CommentResponse commentResponse = new CommentResponse(comment.getId(), memberId, testId, comment.getCommentDate(), comment.getContent(), username);
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

}