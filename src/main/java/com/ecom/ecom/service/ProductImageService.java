package com.ecom.ecom.service;

import com.ecom.ecom.payload.PorductImageDto;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImageService {
    PorductImageDto uploadImage(MultipartFile file, String bucketName);
}
