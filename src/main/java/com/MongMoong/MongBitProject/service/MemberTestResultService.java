package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.MemberTestResult;
import com.MongMoong.MongBitProject.repository.MemberTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberTestResultService {

    private final MemberTestResultRepository memberTestResultRepository;

    public Page<MemberTestResult> getResultsByMemberId(String memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "testDate"));
        return memberTestResultRepository.findByMemberId(memberId, pageable);
    }
}
