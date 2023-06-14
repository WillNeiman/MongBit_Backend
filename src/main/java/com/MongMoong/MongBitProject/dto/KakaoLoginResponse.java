package com.MongMoong.MongBitProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class KakaoLoginResponse {
    private String memberId;
    private String username;
    private String thumbnail;
    private LocalDateTime registDate;
    private String errorMessage;

    public KakaoLoginResponse(String memberId, String username, String thumbnail, LocalDateTime registDate) {
        this.username = username;
        this.memberId = memberId;
        this.thumbnail = thumbnail;
        this.registDate = registDate;
    }

    public KakaoLoginResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}