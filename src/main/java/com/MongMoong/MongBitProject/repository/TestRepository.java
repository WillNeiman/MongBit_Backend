package com.MongMoong.MongBitProject.repository;

import com.MongMoong.MongBitProject.model.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Test, String> {
    Page<Test> save(Pageable pageable);
}
