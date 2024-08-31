package com.ecom.ecom.repository;

import com.ecom.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
boolean existsByEmail(String email);
}