package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.exception.ResourceNotFoundException;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.repository.LikeRepository;
import com.MongMoong.MongBitProject.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final TestRepository testRepository;

    public int getLikesCountByTestId(String testId) {
        //orElseThrow() : Optional 클래스 메소드로 Optional 객체가 감싸고 있는 값이 존재하면 그 값을 반환하고, null이면 괄호 안에 정의된 예외를 발생
        Test test = testRepository.findById(testId).orElseThrow(() -> new ResourceNotFoundException("해당 테스트가 조회되지 않았습니다. " + testId));
        return likeRepository.countByTestId(testId);
    }

    public boolean hasUserLikedTest(String memberId, String testId) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new ResourceNotFoundException("해당 테스트가 조회되지 않았습니다. " + testId));
        return likeRepository.findByMemberIdAndTestId(memberId, testId) != null;
    }
}
