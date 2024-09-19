package com.ecom.ecom.repository;

import com.ecom.ecom.entity.User;
import com.ecom.ecom.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);
    User findByEmail(String email);
    Optional<User> findByName(String name);
    User findByRole(UserRole userRole);


}