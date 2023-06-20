package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.Answer;
import com.MongMoong.MongBitProject.model.TestResult;
import com.MongMoong.MongBitProject.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    public List<Answer> createAnswerList(List<Answer> answerLists){
        return answerRepository.saveAll(answerLists);
    }

    public Answer createAnswer(Answer answer){
        return answerRepository.save(answer);
    }
    public List<Answer> getAnswerList(){
        return answerRepository.findAll();
    }

    public Optional<Answer> getAnswer(String id){
        return answerRepository.findById(id);
    }

    public Answer updateAnswer(Answer answer){
        return answerRepository.save(answer);
    }
    public void deleteAnswer(String id){
        answerRepository.deleteById(id);
    }

}
