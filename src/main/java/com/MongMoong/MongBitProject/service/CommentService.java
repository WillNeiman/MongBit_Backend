package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.aspect.CommentExistenceCheck;
import com.MongMoong.MongBitProject.aspect.CommentLimitCheck;
import com.MongMoong.MongBitProject.aspect.MemberExistenceAtTestCheck;
import com.MongMoong.MongBitProject.aspect.TestExistenceAtCommentCheck;
import com.MongMoong.MongBitProject.dto.CommentDTO;
import com.MongMoong.MongBitProject.dto.CommentResponse;
import com.MongMoong.MongBitProject.exception.BadRequestException;
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

    // 댓글 저장 (신원 검증 필요)
    @MemberExistenceAtTestCheck
    @TestExistenceAtCommentCheck
    @CommentLimitCheck
    public Comment saveComment(Comment comment) {
        comment.setCommentDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    // 댓글 수정 (신원 검증 필요)
    @MemberExistenceAtTestCheck
    @TestExistenceAtCommentCheck
    @CommentExistenceCheck
    @CommentLimitCheck
    public Comment updateComment(Comment comment) {
        Comment existingComment = commentRepository.findById(comment.getId()).orElse(null);
        existingComment.setContent(comment.getContent());
        commentRepository.save(existingComment);
        return existingComment;
    }

    // 댓글 삭제(신원 검증 필요)
    @MemberExistenceAtTestCheck
    @CommentExistenceCheck
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    // 댓글 수 가져오기
    @TestExistenceAtCommentCheck
    public int getCommentsCountByTestId(Comment comment) {
        String testId = comment.getTestId();
        return commentRepository.countByTestId(testId);
    }

    // 모든 댓글 가져오기 (현재 사용 안함)
    @TestExistenceAtCommentCheck
    public List<CommentDTO> getCommentsForTest(Comment comment) {
        String testId = comment.getTestId();
        List<Comment> comments = commentRepository.findByTestId(testId);
        List<String> memberIds = comments.stream().map(Comment::getMemberId).collect(Collectors.toList());
        List<Member> members = memberRepository.findByIdIn(memberIds);
        Map<String, String> memberIdUsernameMap = members.stream().collect(Collectors.toMap(Member::getId, Member::getUsername));
        Map<String, String> memberIdThumbnailMap = members.stream().collect(Collectors.toMap(Member::getId, Member::getThumbnailImage));
        List<CommentDTO> commentResponse = new ArrayList<>();
        for (Comment findComment : comments) {
            String memberId = findComment.getMemberId();
            String username = memberIdUsernameMap.get(memberId);
            String thumbnailImage = memberIdThumbnailMap.get((memberId));
            CommentDTO commentDTO = new CommentDTO(findComment.getId(), memberId, testId, findComment.getCommentDate(), findComment.getContent(), username, thumbnailImage);
            commentResponse.add(commentDTO);
        }
        return commentResponse;
    }

    // 댓글 페이지로 가져오기
    @TestExistenceAtCommentCheck
    public CommentResponse<CommentDTO> getCommentsForTestPaged(Comment comment, int pageNumber) {
        String testId = comment.getTestId();
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("commentDate").descending());
        Page<Comment> commentsPage = commentRepository.findByTestId(testId, pageable);
        List<Comment> comments = commentsPage.getContent();
        List<String> memberIds = comments.stream().map(Comment::getMemberId).collect(Collectors.toList());
        List<Member> members = memberRepository.findByIdIn(memberIds);
        Map<String, String> memberIdUsernameMap = members.stream().collect(Collectors.toMap(Member::getId, Member::getUsername));
        Map<String, String> memberIdThumbnailMap = members.stream().collect(Collectors.toMap(Member::getId, Member::getThumbnailImage));
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment findComment : comments) {
            String memberId = findComment.getMemberId();
            String username = memberIdUsernameMap.get(memberId);
            String thumbnailImage = memberIdThumbnailMap.get((memberId));
            CommentDTO commentDTO = new CommentDTO(findComment.getId(), memberId, testId, findComment.getCommentDate(), findComment.getContent(), username, thumbnailImage);
            commentDTOList.add(commentDTO);
        }
        CommentResponse<CommentDTO> commentResponse = new CommentResponse<>();
        commentResponse.setCommentDTOList(commentDTOList);
        commentResponse.setHasNextPage(commentsPage.hasNext());
        return commentResponse;
    }

}