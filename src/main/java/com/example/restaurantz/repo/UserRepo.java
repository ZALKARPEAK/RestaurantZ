package com.example.restaurantz.repo;

import com.example.restaurantz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String email);
    boolean existsByEmail(String email);
}
