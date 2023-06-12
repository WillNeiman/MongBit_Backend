package com.MongMoong.MongBitProject.repository;

import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.model.TestResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuizRepository extends MongoRepository<Test, String> {

    Test save(Test test);
    //void saveQuestions(List<Question> questionList);
    //void saveTestResults(List<TestResult> resultList);
    //void updateQuestions(List<Question> questionList);
    //void updateTestResults(List<TestResult> resultList);
    void deleteById(String id);

}
