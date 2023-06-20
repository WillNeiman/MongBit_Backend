package com.MongMoong.MongBitProject.dto;

import com.MongMoong.MongBitProject.model.Answer;
import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.model.TestResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TestDTO {
//    private Test test;
    private String title;
    private String content;
    private String imageUrl;
//    private List<Question> questionList;
//    private List<Answer> answerList;
    private List<TestResult> testResultList;

}
