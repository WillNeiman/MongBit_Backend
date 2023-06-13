package com.MongMoong.MongBitProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class KakaoLoginResponse {
    private Long kakaoId;
    private String memberId;
    private String thumbnail;
    private LocalDateTime registDate;
    private String errorMessage;

    public KakaoLoginResponse(Long kakaoId, String memberId, String thumbnail, LocalDateTime registDate) {
        this.kakaoId = kakaoId;
        this.memberId = memberId;
        this.thumbnail = thumbnail;
        this.registDate = registDate;
    }

    public KakaoLoginResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}