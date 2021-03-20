package com.example.portfolio.repository;

import com.example.portfolio.model.Comment;
import com.example.portfolio.model.Post;
import com.example.portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);

}