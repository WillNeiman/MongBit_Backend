package com.MongMoong.MongBitProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class KakaoUserInfo {
    Long id;
    String memberId;
    String email;
    String nickname;
    String thumbnailImage;

    public KakaoUserInfo(Long id, String email, String nickname, String thumbnailImage) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.thumbnailImage = thumbnailImage;
    }
}