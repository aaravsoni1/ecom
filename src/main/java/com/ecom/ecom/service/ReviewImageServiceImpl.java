package com.ecom.ecom.service;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.ecom.ecom.entity.ReviewImage;
import com.ecom.ecom.payload.ReviewImageDto;
import com.ecom.ecom.repository.ReviewImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReviewImageServiceImpl implements ReviewImageService{

    private BucketService bucketService;
    private ReviewImageRepository imageRepository;

    private final String bucketName = "projectecom1";

    public ReviewImageServiceImpl(BucketService bucketService, ReviewImageRepository imageRepository) {
        this.bucketService = bucketService;
        this.imageRepository = imageRepository;
    }
    @Override
    public ReviewImageDto uploadReviewImage(MultipartFile file) {
        String url = bucketService.uploadProductImage(file, bucketName);
        ReviewImage image = new ReviewImage();
        image.setImage_url(url);
        ReviewImage saved = imageRepository.save(image);
        ReviewImageDto dto = new ReviewImageDto();
        dto.setId(saved.getId());
        dto.setImageUrl(saved.getImage_url());
        return dto;
    }

    @Override
    public ReviewImageDto deleteReviewImage(String fileName) {
        ReviewImage image = imageRepository.findByImage_url(fileName);
        if(image!= null){
            imageRepository.delete(image);
            bucketService.deleteReviewImage(bucketName, fileName);
        }

        return null;
    }
}
