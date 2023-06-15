package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.aspect.TestExistenceCheck;
import com.MongMoong.MongBitProject.exception.ResourceNotFoundException;
import com.MongMoong.MongBitProject.model.Like;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.repository.LikeRepository;
import com.MongMoong.MongBitProject.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    @TestExistenceCheck
    public int getLikesCountByTestId(String testId) {
        return likeRepository.countByTestId(testId);
    }

    @TestExistenceCheck
    public boolean hasUserLikedTest(String memberId, String testId) {
        return likeRepository.findByMemberIdAndTestId(memberId, testId) != null;
    }

    @TestExistenceCheck
    public Like createLike(String memberId, String testId) {
        LocalDateTime likeDate = LocalDateTime.now();
        Like like = new Like(memberId, testId, likeDate);
        return likeRepository.save(like);
    }

    @TestExistenceCheck
    public void deleteLike(String memberId, String testId) {
        Like like = likeRepository.findByMemberIdAndTestId(memberId, testId);
        likeRepository.delete(like);
    }
}
