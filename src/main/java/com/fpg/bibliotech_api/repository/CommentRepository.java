package com.fpg.bibliotech_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpg.bibliotech_api.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    
    List<Comment> findByBookId(Integer bookId);
    List<Comment> findByUserId(Integer userId);
    List<Comment> findByUserIdAndBookId(Integer userId, Integer bookId);

}
