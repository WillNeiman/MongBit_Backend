package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.Answer;
import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.repository.AnswerRepository;
import com.MongMoong.MongBitProject.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> createQuestionList(List<Question> questionLists){
        return questionRepository.saveAll(questionLists);
    }
    public Question createQuestion(Question question){
        return questionRepository.save(question);
    }

    public List<Question> getQuestionList(){
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestion(String id){
        return questionRepository.findById(id);
    }

    public Question updateQuestion(Question question){
        return questionRepository.save(question);
    }
    public void deleteQuestion(String id){
        questionRepository.deleteById(id);
    }
}
