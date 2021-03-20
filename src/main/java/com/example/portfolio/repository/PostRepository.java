package com.example.portfolio.repository;

import com.example.portfolio.model.Post;
import com.example.portfolio.model.Subfite;
import com.example.portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubfite(Subfite subfite);
    List<Post> findByUser (User user);
}
