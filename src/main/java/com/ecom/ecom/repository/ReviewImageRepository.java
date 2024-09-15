package com.ecom.ecom.repository;

import com.ecom.ecom.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    @Query(value = "SELECT r.* FROM ReviewImage r WHERE r.image_url = :imageName", nativeQuery = true)
    ReviewImage findByImage_url(@Param("imageName") String imageName);
}