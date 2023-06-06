package com.MongMoong.MongBitProject.repository;

import com.MongMoong.MongBitProject.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
