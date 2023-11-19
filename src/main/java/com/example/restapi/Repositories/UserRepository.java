package com.example.restapi.Repositories;

import com.example.restapi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
   User findByUsername(String username);
   boolean existsByUsername(String userName);
   boolean existsByEmail(String email);
}
