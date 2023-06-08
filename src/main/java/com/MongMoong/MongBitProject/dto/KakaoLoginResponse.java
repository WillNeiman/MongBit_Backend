package com.MongMoong.MongBitProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class KakaoLoginResponse {
    private String thumbnail;
    private LocalDateTime registDate;
    private String errorMessage;

    public KakaoLoginResponse(String thumbnail, LocalDateTime registDate) {
        this.thumbnail = thumbnail;
        this.registDate = registDate;
    }

    public KakaoLoginResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}