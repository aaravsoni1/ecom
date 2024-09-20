package com.ecom.ecom.repository;

import com.ecom.ecom.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query(value = "SELECT p* FROM ProductImage WHERE p.image_url = :imageUrl", nativeQuery = true)
    ProductImage findByImageUrl(@Param("imageUrl") String imageUrl);

}
