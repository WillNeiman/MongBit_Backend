package com.MongMoong.MongBitProject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("Like")
@Getter
@Setter
public class Like {
    @Id
    private String id;
    private String memberId;
    private String testId;
    private LocalDateTime likeDate;

    public Like(String memberId, String testId, LocalDateTime likeDate) {
        this.memberId = memberId;
        this.testId = testId;
        this.likeDate = likeDate;
    }
}
