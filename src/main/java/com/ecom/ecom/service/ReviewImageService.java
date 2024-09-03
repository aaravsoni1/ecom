package com.ecom.ecom.service;

import com.ecom.ecom.payload.ReviewImageDto;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewImageService {
    public ReviewImageDto uploadReviweImage(MultipartFile file, String bucketName);
}
