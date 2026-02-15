package com.backend.microservices.repository;

import com.backend.microservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    void deleteByUsername(String username);

    Optional<User> findByUsername(String username);
}
