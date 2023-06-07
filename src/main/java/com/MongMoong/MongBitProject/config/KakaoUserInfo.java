package com.MongMoong.MongBitProject.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KakaoUserInfo {
    Long id;
    String email;
    String nickname;
    String thumbnailImage;
}