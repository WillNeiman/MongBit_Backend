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
    /*
    if property == IE
    result[0] + score

    if property == NS
    result[1] + score

    if property == FT
    result[2] + score

    if property == PJ
    result[3] + score

    or
    property = I/E/N/S/F/T/P/J
    if property == I
    result[0] +1

    if property == E
    result[0] -1
     */
}
