package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.dto.CommentResponse;
import com.MongMoong.MongBitProject.model.Comment;
import com.MongMoong.MongBitProject.model.Like;
import com.MongMoong.MongBitProject.model.Member;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.repository.LikeRepository;
import com.MongMoong.MongBitProject.repository.MemberRepository;
import com.MongMoong.MongBitProject.repository.TestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class LikeServiceTest {

    @Autowired
    private LikeService likeService;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TestService testService;
    @Autowired
    private TestRepository testRepository;

    private Member member;
    private Test test;
    private Like like;

    @BeforeEach
    public void setup() {
        // 테스트 멤버 생성
        member = new Member();
        member.setId("testMemberId");
        memberRepository.save(member);
        System.out.println("테스트용 계정: " + member.toString());

        // 테스트 심테 생성
        test = new Test();
        test.setId("testTestId");
        testRepository.save(test);
        System.out.println("테스트용 심테: " + test.toString());

        // 테스트 좋아요 생성
        like = new Like();
        like.setMemberId(member.getId());
        like.setTestId(test.getId());
        like.setLikeDate(LocalDateTime.now());
        likeRepository.save(like);
        System.out.println("테스트용 좋아요: " + like.toString());
    }

    @AfterEach
    public void tearDown() {
        // delete test data
        likeRepository.delete(like);
        System.out.println("테스트용 좋아요 삭제: " + like.toString());
        testRepository.delete(test);
        System.out.println("테스트용 심테 삭제: " + test.toString());
        memberRepository.delete(member);
        System.out.println("테스트용 멤버 삭제: " + member.toString());
    }

    @org.junit.jupiter.api.Test
    public void getCommentsForTest_Success() {
        String testId = test.getId();
        String memberId = member.getId();
        Like findLike = likeRepository.findByTestIdAndMemberId(testId, memberId);

        assertFalse(findLike == null, "Comment list should not be empty");
        System.out.println("조회된 좋아요: " + findLike.toString());
        assertEquals("testTestId", findLike.getTestId(), "Content should be 'test content'");
        assertEquals("testMemberId", findLike.getMemberId(), "Username should be 'testUsername'");
    }
}
