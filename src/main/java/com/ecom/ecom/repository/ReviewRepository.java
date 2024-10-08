package com.ecom.ecom.repository;

import com.ecom.ecom.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(nativeQuery = true, value = "SELECT r.* FROM Review r WHERE r.user_id = :userId AND r.product_id = :productId")
    Review findReviewByProduct(@Param("userId") Long u, @Param("productId") Long p);

    @Query(nativeQuery = true, value = "SELECT r.* FROM Review r WHERE r.product_id = :productId")
    List<Review> getAllReviewsByProduct(@Param("productId")Long productId);

    Review findReviewById(Long id);
}