package com.example.restapi.Repositories;

import com.example.restapi.Models.userlogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<userlogin, Integer> {
   Optional<userlogin> findByUsername(String username);
}
