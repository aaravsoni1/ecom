package com.ecom.ecom.repository;

import com.ecom.ecom.entity.User;
import com.ecom.ecom.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);

   User findByRole(UserRole userRole);


//    boolean existsByUsername(String username);
//boolean existsByEmail(String email);
}