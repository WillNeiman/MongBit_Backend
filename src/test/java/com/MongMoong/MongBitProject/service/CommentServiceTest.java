package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.dto.CommentResponse;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.repository.CommentRepository;
import com.MongMoong.MongBitProject.repository.MemberRepository;
import com.MongMoong.MongBitProject.repository.TestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Member member;
    private Test test;
    private Comment comment;

    @BeforeEach
    public void setup() {
        // 테스트 멤버 생성
        member = new Member();
        member.setId("testMemberId");
        member.setUsername("testUsername");
        memberRepository.save(member);
        System.out.println("테스트용 계정: " + member.toString());

        // 테스트 심테 생성
        test = new Test();
        test.setId("testTestId");
        testRepository.save(test);
        System.out.println("테스트용 심테: " + test.toString());

        // 테스트 댓글 생성
        comment = new Comment();
        comment.setMemberId(member.getId());
        comment.setTestId(test.getId());
        comment.setCommentDate(LocalDateTime.now());
        comment.setContent("test content");
        commentRepository.save(comment);
        System.out.println("테스트용 댓글: " + comment.toString());
    }

    @AfterEach
    public void tearDown() {
        // delete test data
        commentRepository.delete(comment);
        System.out.println("테스트용 코멘트 삭제: " + comment.toString());
        testRepository.delete(test);
        System.out.println("테스트용 심테 삭제: " + test.toString());
        memberRepository.delete(member);
        System.out.println("테스트용 멤버 삭제: " + member.toString());
    }

    @org.junit.jupiter.api.Test
    public void getCommentsForTest_Success() {
        String testId = test.getId();
        comment.setTestId(testId);
        List<CommentResponse> comments = commentService.getCommentsForTest(comment);

        assertFalse(comments.isEmpty(), "Comment list should not be empty");
        assertEquals(1, comments.size(), "Should have 1 comment");
        CommentResponse commentResponse = comments.get(0);
        System.out.println("조회된 댓글: " + commentResponse.toString());
        assertEquals("test content", commentResponse.getContent(), "Content should be 'test content'");
        assertEquals("testUsername", commentResponse.getUsername(), "Username should be 'testUsername'");
    }
}
