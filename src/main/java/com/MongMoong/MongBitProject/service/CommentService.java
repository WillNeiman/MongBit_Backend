package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.aspect.CommentExistenceCheck;
import com.MongMoong.MongBitProject.aspect.TestExistenceAtCommentCheck;
import com.MongMoong.MongBitProject.dto.CommentResponse;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.repository.CommentRepository;
import com.MongMoong.MongBitProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final MemberRepository memberRepository;

    @TestExistenceAtCommentCheck
    public Comment saveComment(Comment comment) {
        comment.setCommentDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @TestExistenceAtCommentCheck
    @CommentExistenceCheck
    public Comment updateComment(Comment comment) {
        Comment existingComment = commentRepository.findById(comment.getId()).orElse(null);
        if(existingComment != null){
            existingComment.setContent(comment.getContent());
            commentRepository.save(existingComment);
        }
        return existingComment;
    }

    @CommentExistenceCheck
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @TestExistenceAtCommentCheck
    public List<CommentResponse> getCommentsForTest(Comment comment) {
        String testId = comment.getTestId();
        List<Comment> comments = commentRepository.findByTestId(testId);
        List<String> memberIds = comments.stream().map(Comment::getMemberId).collect(Collectors.toList());
        List<Member> members = memberRepository.findByIdIn(memberIds);
        Map<String, String> memberIdUsernameMap = members.stream().collect(Collectors.toMap(Member::getId, Member::getUsername));
        Map<String, String> memberIdThumbnailMap = members.stream().collect(Collectors.toMap(Member::getId, Member::getThumbnailImage));
        List<CommentResponse> commentResponses = new ArrayList<>();
        for(Comment findComment : comments) {
            String memberId = findComment.getMemberId();
            String username = memberIdUsernameMap.get(memberId);
            String thumbnailImage = memberIdThumbnailMap.get((memberId));
            CommentResponse commentResponse = new CommentResponse(findComment.getId(), memberId, testId, findComment.getCommentDate(), findComment.getContent(), username, thumbnailImage);
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

    @TestExistenceAtCommentCheck
    public List<CommentResponse> getCommentsForTestPaged(Comment comment, int pageNumber) {
        String testId = comment.getTestId();
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("commentDate").descending());
        Page<Comment> commentsPage = commentRepository.findByTestId(testId, pageable);
        List<Comment> comments = commentsPage.getContent();
        List<String> memberIds = comments.stream().map(Comment::getMemberId).collect(Collectors.toList());
        List<Member> members = memberRepository.findByIdIn(memberIds);
        Map<String, String> memberIdUsernameMap = members.stream().collect(Collectors.toMap(Member::getId, Member::getUsername));
        Map<String, String> memberIdThumbnailMap = members.stream().collect(Collectors.toMap(Member::getId, Member::getThumbnailImage));
        List<CommentResponse> commentResponses = new ArrayList<>();
        for(Comment findComment : comments) {
            String memberId = findComment.getMemberId();
            String username = memberIdUsernameMap.get(memberId);
            String thumbnailImage = memberIdThumbnailMap.get((memberId));
            CommentResponse commentResponse = new CommentResponse(findComment.getId(), memberId, testId, findComment.getCommentDate(), findComment.getContent(), username, thumbnailImage);
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

}