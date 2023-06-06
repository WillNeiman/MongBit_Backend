package com.MongMoong.MongBitProject.repository;

import com.MongMoong.MongBitProject.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LikeRepository extends MongoRepository<Like, String> {
}
