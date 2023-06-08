package com.MongMoong.MongBitProject.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class KakaoLoginResponse {
    private String thumbnail;
    private LocalDateTime registDate;

    public KakaoLoginResponse(String thumbnail, LocalDateTime registDate) {
        this.thumbnail = thumbnail;
        this.registDate = registDate;
    }
}
