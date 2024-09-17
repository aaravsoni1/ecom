package com.ecom.ecom.service;

import com.ecom.ecom.payload.ReviewImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewImageService {

    ReviewImageDto uploadReviewImage(MultipartFile file);
    ReviewImageDto deleteReviewImage(String fileName);
    List<ReviewImageDto> uploadReviewImages(MultipartFile [] files);
}
