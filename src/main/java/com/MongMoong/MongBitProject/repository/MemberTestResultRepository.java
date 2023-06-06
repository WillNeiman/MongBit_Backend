package com.MongMoong.MongBitProject.repository;

import com.MongMoong.MongBitProject.model.MemberTestResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberTestResultRepository extends MongoRepository<MemberTestResult, String> {
}
