package com.MongMoong.MongBitProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCoverResponse {
    //Test
    private String id;
    private String title;
    private String imageUrl;
    private int playCount;

    //Like
    private int likeCount;

    //Comment
    private int commentCount;

    public TestCoverResponse(String id, String title, String imageUrl, int playCount) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.playCount = playCount;
    }
}
