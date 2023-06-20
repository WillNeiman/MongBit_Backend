package com.MongMoong.MongBitProject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Document("Answer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    private String id;
    private int index;
    private String property; //속성 - IE/NS/FT/PJ
    private int score;
    private String answer;

}
