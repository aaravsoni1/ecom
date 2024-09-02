package com.ecom.ecom.repository;

import com.ecom.ecom.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}