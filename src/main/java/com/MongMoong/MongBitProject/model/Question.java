package com.MongMoong.MongBitProject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Question")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    private String id;
    private int index; // 질문의 순서
    private String property; //속성 - I(+1),E(-1)/N(+1),S(-1)/F(+1),T(-1)/P(+1),J(-1)
    private String question;
}
