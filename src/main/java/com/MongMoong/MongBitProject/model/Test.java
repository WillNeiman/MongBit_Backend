package com.MongMoong.MongBitProject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("Test")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    private String id;
    private String title;
    private String content;
    private List<Question> questions;
    private List<TestResult> results;
    private LocalDateTime createDate;
    private String imageUrl;
    private int playCount;

}

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Question {
    @Id
    private String id;
    private int index; // 질문의 순서
    private String question;
}

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class TestResult {
    @Id
    private String id;
    private String title;
    private String content;
    private String imageUrl;
}
