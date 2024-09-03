package com.ecom.ecom.service;

import com.ecom.ecom.entity.ReviewImage;
import com.ecom.ecom.payload.ReviewImageDto;
import com.ecom.ecom.repository.ReviewImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReviewImageServiceImpl implements ReviewImageService{

    private BucketService bucketService;
    private ReviewImageRepository imageRepository;

    public ReviewImageServiceImpl(BucketService bucketService, ReviewImageRepository imageRepository) {
        this.bucketService = bucketService;
        this.imageRepository = imageRepository;
    }
    @Override
    public ReviewImageDto uploadReviweImage(MultipartFile file, String bucketName) {
        String url = bucketService.uploadProductImage(file, bucketName);
        ReviewImage image = new ReviewImage();
        image.setImage_url(url);
        ReviewImage saved = imageRepository.save(image);
        ReviewImageDto dto = new ReviewImageDto();
        dto.setIg(saved.getId());
        dto.setImageUrl(saved.getImage_url());
        return dto;
    }
}
