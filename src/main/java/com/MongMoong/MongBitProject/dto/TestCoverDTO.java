package com.MongMoong.MongBitProject.dto;

import com.MongMoong.MongBitProject.model.TestResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TestCoverDTO {
    //Test
    private String id;
    private String title;
    private String imageUrl;
    private int playCount;

    //Like
    private int likeCount;

    //Comment
    private int commentCount;

    public TestCoverDTO(String id, String title, String imageUrl, int playCount) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.playCount = playCount;
    }

}
