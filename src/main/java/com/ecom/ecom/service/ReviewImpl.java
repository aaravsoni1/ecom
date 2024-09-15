package com.ecom.ecom.service;

import com.ecom.ecom.entity.Product;
import com.ecom.ecom.entity.Review;
import com.ecom.ecom.entity.User;
import com.ecom.ecom.payload.ReviewDto;
import com.ecom.ecom.payload.ReviewImageDto;
import com.ecom.ecom.repository.ProductRepository;
import com.ecom.ecom.repository.ReviewImageRepository;
import com.ecom.ecom.repository.ReviewRepository;
import com.ecom.ecom.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

@Service

public class ReviewImpl implements ReviewService{

    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private ReviewImageServiceImpl imageService;
    private final ReviewImageRepository reviewImageRepository;

    public ReviewImpl(ReviewRepository reviewRepository,
                      ProductRepository productRepository,
                      UserRepository userRepository,
                      ReviewImageServiceImpl imageService,
                      ReviewImageRepository reviewImageRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.reviewImageRepository = reviewImageRepository;
    }



    private ReviewDto EntityToDto(Review entity) {
        ReviewDto dto = new ReviewDto();
        dto.setId(entity.getId());
        dto.setComment(entity.getComment());
        dto.setRating(entity.getRating());
        dto.setImage_url(entity.getImage_url());
        dto.setCreated_at(entity.getCreated_at());
        dto.setProduct_id(entity.getProduct().getId());
        dto.setUser_id(entity.getUser().getId());
        return dto;
    }

    public Review DtoToEntity(ReviewDto dto){
        Review entity = new Review();
        entity.setId(dto.getId());
        entity.setComment(dto.getComment());
        entity.setRating(dto.getRating());
        entity.setCreated_at(new Date());
        return entity;
    }

    @Override
    public ReviewDto addReview(ReviewDto review, Long product_id, UserDetails userDetails, MultipartFile file) {
        Optional<Product> opProduct = productRepository.findProductById(product_id);
        String username = userDetails.getUsername();

        Optional<User> opUser = userRepository.findByName(username);
        if(opProduct.isPresent() && opUser.isPresent()) {
            Review entity = DtoToEntity(review);
            entity.setProduct(opProduct.get());
            entity.setUser(opUser.get());
            ReviewImageDto imageDto = imageService.uploadReviewImage(file);
            entity.setImage_url(imageDto.getImageUrl());
            Review saved = reviewRepository.save(entity);
            return EntityToDto(saved);
        }
        return null;
    }

    @Override
    public ReviewDto getReviewById(Long reviewId) {
        Review entity = reviewRepository.findById(reviewId).orElse(null);
        if (entity!= null) {
            return EntityToDto(entity);
        }
        return null;
    }

    @Override
    public ReviewDto updateReview(ReviewDto dto) {
        Review review = reviewRepository.findById(dto.getId()).orElse(null);
        if(review != null){
            review.setUpdated_at(new Date());
            ReviewDto reviewDto = EntityToDto(review);
            reviewRepository.save(review);
            return reviewDto;
        }
        return null;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        boolean b = reviewRepository.existsById(reviewId);
        if(b){
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }

    @Override
    public ReviewDto getAllReviews(Long productId) {
        Iterable<Review> all = reviewRepository.getAllReviewsByProduct(productId);
        while(all.iterator().hasNext()) {
            ReviewDto dto = EntityToDto(all.iterator().next());
            return dto;
        }
        return null;
    }

    @Override
    public Review verifyUser(UserDetails userDetails, Long product_id) {
        Optional<User> user = userRepository.findByName(userDetails.getUsername());
        Optional<Product> opProduct = productRepository.findProductById(product_id);
        if(opProduct.isPresent()) {
            Long p = opProduct.get().getId();
            Long u = user.get().getId();
            Review entity = reviewRepository.findReviewByProduct(u, p);
            return entity;
        }
        return null;
    }


}
