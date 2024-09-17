package com.ecom.ecom.service;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.ecom.ecom.entity.ReviewImage;
import com.ecom.ecom.payload.ReviewImageDto;
import com.ecom.ecom.repository.ReviewImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        String url = bucketService.uploadReviewImage(file, bucketName);
        ReviewImage image = new ReviewImage();
        image.setImage_url(url);
        ReviewImage saved = imageRepository.save(image);
        ReviewImageDto dto = new ReviewImageDto();
        dto.setId(saved.getId());
        dto.setImageUrl(saved.getImage_url());
        return dto;
    }

    public ReviewImageDto EntityToDto(ReviewImage entity){
        ReviewImageDto dto = new ReviewImageDto();
        dto.setId(entity.getId());
        dto.setImageUrl(entity.getImage_url());
        return dto;
    }

    public ReviewImage DtoToEntity(ReviewImageDto dto){
        ReviewImage entity = new ReviewImage();
        entity.setId(dto.getId());
        entity.setImage_url(dto.getImageUrl());
        return entity;
    }

    public ReviewImage setUrls(String url){
        ReviewImage reviewImage = new ReviewImage();
        reviewImage.setImage_url(url);
        imageRepository.save(reviewImage);
        return reviewImage;
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

    @Override
    public List<ReviewImageDto> uploadReviewImages(MultipartFile[] files) {
        try {
            List<String> urls = bucketService.uploadMultipleImageFiles(files, bucketName);
            List<ReviewImage> images = urls.stream().map(url -> setUrls(url)).collect(Collectors.toList());
            List<ReviewImageDto> dtoList = new ArrayList<ReviewImageDto>();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}
