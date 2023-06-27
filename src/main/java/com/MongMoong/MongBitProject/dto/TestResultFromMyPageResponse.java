package com.MongMoong.MongBitProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TestResultFromMyPageResponse {
    private String testId;
    private String result;
    private String title;
    private String content;
    private String imageUrl;
    private int likeCount;

    public TestResultFromMyPageResponse(String result, String title, String content, String imageUrl) {
        this.result = result;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

}
