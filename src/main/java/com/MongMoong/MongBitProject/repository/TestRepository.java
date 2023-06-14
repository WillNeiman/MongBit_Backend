package com.MongMoong.MongBitProject.repository;

import com.MongMoong.MongBitProject.model.Question;
import com.MongMoong.MongBitProject.model.Test;
import com.MongMoong.MongBitProject.model.TestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;
import java.util.Optional;

public interface TestRepository extends MongoRepository<Test, String> {
    Page<Test> findByOrderByCreateDateDesc(Pageable pageable);

    Optional<Test> findById(String id);
    List<Question> findQuestionById(String id);
    Optional<TestResult> findTestResultById(String id, String result);

}
