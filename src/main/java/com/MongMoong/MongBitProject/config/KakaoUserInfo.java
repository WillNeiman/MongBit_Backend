package com.MongMoong.MongBitProject.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class KakaoUserInfo {
    Long id;
    String email;
    String nickname;
    String thumbnailImage;
    LocalDateTime registDate;

    public KakaoUserInfo(Long id, String email, String nickname, String thumbnailImage) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.thumbnailImage = thumbnailImage;
    }
}