package com.MongMoong.MongBitProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TestResultResponse {
    private String result;
    private String title;
    private String content;
    private String imageUrl;
}

