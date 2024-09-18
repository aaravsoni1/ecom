package com.ecom.ecom.service;

import com.ecom.ecom.entity.Review;
import com.ecom.ecom.payload.ReviewImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ReviewImageService {
    ReviewImageDto deleteReviewImage(String fileName);
    List<String> uploadReviewImages(MultipartFile [] files);
}
