package com.ecom.ecom.service;

import com.ecom.ecom.entity.ProductImage;
import com.ecom.ecom.payload.ProductImageDto;
import com.ecom.ecom.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService{

    private BucketService bucketService;

    private ProductImageRepository productImageRepository;
    public ProductImageServiceImpl(BucketService bucketService, ProductImageRepository productImageRepository) {
        this.bucketService = bucketService;
        this.productImageRepository = productImageRepository;
    }
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Override
    public List<String> uploadImage(MultipartFile[] file) {
        try {
            List<String> urls = bucketService.uploadMultipleProductImageFiles(file, bucketName);
            return urls;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteProductImage(String filename) {
        ProductImage image = productImageRepository.findByImageUrl(filename);
        if(image!= null){
            productImageRepository.delete(image);
            bucketService.deleteProductImage(bucketName, filename);
        }
        return null;
    }
}
