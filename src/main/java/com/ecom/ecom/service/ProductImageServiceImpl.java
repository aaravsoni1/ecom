package com.ecom.ecom.service;

import com.ecom.ecom.entity.ProductImage;
import com.ecom.ecom.payload.PorductImageDto;
import com.ecom.ecom.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductImageServiceImpl implements ProductImageService{

    private BucketService bucketService;

    private ProductImageRepository productImageRepository;
    public ProductImageServiceImpl(BucketService bucketService, ProductImageRepository productImageRepository) {
        this.bucketService = bucketService;
        this.productImageRepository = productImageRepository;
    }
    @Override
    public PorductImageDto uploadImage(MultipartFile file, String bucketName) {
        String url = bucketService.uploadProductImage(file, bucketName);
        ProductImage entity = new ProductImage();
        entity.setImage_url(url);
        ProductImage saved = productImageRepository.save(entity);
        PorductImageDto dto = new PorductImageDto();
        dto.setIg(saved.getId());
        dto.setImageUrl(saved.getImage_url());
        return dto;
    }
}
