package com.MongMoong.MongBitProject.repository;

import com.MongMoong.MongBitProject.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByTestId(String testId);
}
