package com.ecom.ecom.service;

import com.ecom.ecom.entity.Product;
import com.ecom.ecom.entity.Review;
import com.ecom.ecom.entity.ReviewImage;
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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // Null check for reviewImages
        if (entity.getReviewImages() != null) {
            dto.setImage_url(entity.getReviewImages().stream()
                    .flatMap(reviewImage -> reviewImage.getImageUrls().stream())
                    .collect(Collectors.toList()));
        } else {
            dto.setImage_url(Collections.emptyList()); // Set an empty list if no images
        }

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
    public ReviewDto addReview(ReviewDto reviewDto, Long product_id, UserDetails userDetails, MultipartFile[] files) {
        // Fetch the product and user
        Optional<Product> opProduct = productRepository.findProductById(product_id);
        String username = userDetails.getUsername();
        Optional<User> opUser = userRepository.findFirstByEmail(username);

        if (opProduct.isPresent() && opUser.isPresent()) {
            // Convert DTO to Review entity
            Review entity = DtoToEntity(reviewDto);
            entity.setProduct(opProduct.get());
            entity.setUser(opUser.get());

            // Save the Review entity first
            Review savedReview = reviewRepository.save(entity);

            // Upload images and get URLs
            List<String> urls = imageService.uploadReviewImages(files);

            // Create and save ReviewImage entities using the savedReview
            List<ReviewImage> reviewImages = urls.stream()
                    .map(url -> {
                        ReviewImage reviewImage = new ReviewImage();
                        reviewImage.setImageUrls(List.of(url));
                        reviewImage.setReview(savedReview); // Use the savedReview with a valid ID
                        return reviewImage;
                    })
                    .collect(Collectors.toList());

            // Save ReviewImage entities to the repository
            reviewImageRepository.saveAll(reviewImages);

            // Convert the saved Review entity to DTO and return
            return EntityToDto(savedReview);
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
    public ReviewDto updateReview(Long reviewid ,ReviewDto dto) {
        Review review = reviewRepository.findById(reviewid).orElse(null);
        if(review != null){
            review.setUpdated_at(new Date());
            review.setRating(dto.getRating());
            review.setComment(dto.getComment());
            Review saved = reviewRepository.save(review);
            return EntityToDto(saved);
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
    public List<ReviewDto> getAllReviews(Long productId) {
        List<Review> all = reviewRepository.getAllReviewsByProduct(productId);
        List<ReviewDto> allReviews = all.stream().map(review -> EntityToDto(review)).collect(Collectors.toList());
        return allReviews;
    }

    @Override
    public Review verifyUser(UserDetails userDetails, Long product_id) {
        Optional<User> user = userRepository.findFirstByEmail(userDetails.getUsername());
        Optional<Product> opProduct = productRepository.findProductById(product_id);
        if(opProduct.isPresent() && user.isPresent()) {
            Long p = opProduct.get().getId();
            Long u = user.get().getId();
            Review entity = reviewRepository.findReviewByProduct(u, p);
            return entity;
        }
        return null;
    }
}
