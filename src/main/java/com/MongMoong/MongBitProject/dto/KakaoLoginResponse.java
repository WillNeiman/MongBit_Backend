package com.MongMoong.MongBitProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class KakaoLoginResponse {
    private String memberId;
    private String userName;
    private String thumbnail;
    private LocalDateTime registDate;
    private String errorMessage;

    public KakaoLoginResponse(String memberId, String userName, String thumbnail, LocalDateTime registDate) {
        this.userName = userName;
        this.memberId = memberId;
        this.thumbnail = thumbnail;
        this.registDate = registDate;
    }

    public KakaoLoginResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}