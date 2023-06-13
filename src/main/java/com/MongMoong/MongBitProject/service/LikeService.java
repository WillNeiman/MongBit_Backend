package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public int getLikesCountByTestId(String testId) {
        return likeRepository.countByTestId(testId);
    }

    public boolean hasUserLikedTest(String memberId, String testId) {
        return likeRepository.findByMemberIdAndTestId(memberId, testId) != null;
    }
}
