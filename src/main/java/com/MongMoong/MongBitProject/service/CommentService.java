package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.aspect.CommentExistenceCheck;
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

    @TestExistenceAtCommentCheck
    public Comment saveComment(Comment comment) {
        comment.setCommentDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @TestExistenceAtCommentCheck
    @CommentExistenceCheck
    public Comment updateComment(Comment comment) {
        Comment existingComment = commentRepository.findById(comment.getId()).orElse(null);
        if(existingComment != null && existingComment.getMemberId().equals(comment.getMemberId())){
            existingComment.setContent(comment.getContent());
            commentRepository.save(existingComment);
            return existingComment;
        } else {
            throw new BadRequestException("자신이 작성한 댓글만 수정할 수 있습니다.");
        }
    }

    @CommentExistenceCheck
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @TestExistenceAtCommentCheck
    public int getCommentsCountByTestId(Comment comment) {
        String testId = comment.getTestId();
        return commentRepository.countByTestId(testId);
    }

    @TestExistenceAtCommentCheck
    public List<CommentDTO> getCommentsForTest(Comment comment) {
        String testId = comment.getTestId();
        List<Comment> comments = commentRepository.findByTestId(testId);
        List<String> memberIds = comments.stream().map(Comment::getMemberId).collect(Collectors.toList());
        List<Member> members = memberRepository.findByIdIn(memberIds);
        Map<String, String> memberIdUsernameMap = members.stream().collect(Collectors.toMap(Member::getId, Member::getUsername));
        Map<String, String> memberIdThumbnailMap = members.stream().collect(Collectors.toMap(Member::getId, Member::getThumbnailImage));
        List<CommentDTO> commentResponse = new ArrayList<>();
        for(Comment findComment : comments) {
            String memberId = findComment.getMemberId();
            String username = memberIdUsernameMap.get(memberId);
            String thumbnailImage = memberIdThumbnailMap.get((memberId));
            CommentDTO commentDTO = new CommentDTO(findComment.getId(), memberId, testId, findComment.getCommentDate(), findComment.getContent(), username, thumbnailImage);
            commentResponse.add(commentDTO);
        }
        return commentResponse;
    }

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
        for(Comment findComment : comments) {
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