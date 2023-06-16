package com.MongMoong.MongBitProject.repository;

import com.MongMoong.MongBitProject.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String>, PagingAndSortingRepository<Comment, String> {

    List<Comment> findByTestId(String testId);

    Page<Comment> findByTestId(String testId, Pageable pageable);
}
