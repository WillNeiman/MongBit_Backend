package com.MongMoong.MongBitProject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("Like")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    private String id;
    private long kakaoId;
    private String testId;
    private LocalDateTime likeDate;
}
