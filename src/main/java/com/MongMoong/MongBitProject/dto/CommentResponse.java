package com.MongMoong.MongBitProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponse {
    private String id;
    private String memberId;
    private String testId;
    private LocalDateTime commentDate;
    private String content;
    private String username;
    private String thumbnailImage;
}