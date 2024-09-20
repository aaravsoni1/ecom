package com.ecom.ecom.service;

import com.ecom.ecom.payload.ProductImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    List<String> uploadImage(MultipartFile[] file);
    String deleteProductImage(String filename);
}
