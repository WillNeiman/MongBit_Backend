package com.MongMoong.MongBitProject.service;

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

    @TestExistenceAtLikeCheck
    public Like createLike(String testId, String memberId) {
        LocalDateTime likeDate = LocalDateTime.now();
        Like like = new Like(memberId, testId, likeDate);
        return likeRepository.save(like);
    }

    @TestExistenceAtLikeCheck
    public void deleteLike(String testId, String memberId) {
        Like like = likeRepository.findByTestIdAndMemberId(testId, memberId);
        likeRepository.delete(like);
    }
}
