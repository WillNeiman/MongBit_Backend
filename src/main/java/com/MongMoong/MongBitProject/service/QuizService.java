package com.MongMoong.MongBitProject.service;

import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.repository.QuizRepository;
import com.MongMoong.MongBitProject.repository.TestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }


}
