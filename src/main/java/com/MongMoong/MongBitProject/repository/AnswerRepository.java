package com.MongMoong.MongBitProject.repository;

import com.MongMoong.MongBitProject.model.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer, String> {
}
