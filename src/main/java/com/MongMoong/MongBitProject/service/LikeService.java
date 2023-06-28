package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.aspect.MemberExistenceAtTestCheck;
import com.MongMoong.MongBitProject.aspect.TestExistenceAtCommentCheck;
import com.MongMoong.MongBitProject.aspect.TestExistenceAtLikeCheck;
import com.MongMoong.MongBitProject.model.Like;
import com.MongMoong.MongBitProject.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    @TestExistenceAtLikeCheck
    public int getLikesCountByTestId(String testId) {
        return likeRepository.countByTestId(testId);
    }

    @TestExistenceAtLikeCheck
    public boolean hasUserLikedTest(String testId, String memberId) {
        return likeRepository.findByTestIdAndMemberId(testId, memberId) != null;
    }

    @MemberExistenceAtTestCheck
    @TestExistenceAtLikeCheck
    public synchronized Like createLike(String testId, String memberId) {
        Like existLike = likeRepository.findByTestIdAndMemberId(testId, memberId);
        if(existLike == null) {
            LocalDateTime likeDate = LocalDateTime.now();
            Like like = new Like(memberId, testId, likeDate);
            return likeRepository.save(like);
        }
        return null;
    }

    @MemberExistenceAtTestCheck
    @TestExistenceAtLikeCheck
    public void deleteLike(String testId, String memberId) {
        Like existLike = likeRepository.findByTestIdAndMemberId(testId, memberId);
        if(existLike != null) {
            Like like = likeRepository.findByTestIdAndMemberId(testId, memberId);
            likeRepository.delete(like);
        }
    }
}
