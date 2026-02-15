package com.backend.microservices.repository;

import com.backend.microservices.model.Blog;
import com.backend.microservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    // Get all blogs of a specific user
    List<Blog> findByUser(User user);
}
