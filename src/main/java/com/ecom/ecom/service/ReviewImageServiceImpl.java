package com.ecom.ecom.service;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.ecom.ecom.entity.Review;
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
    public ReviewImageDto deleteReviewImage(String fileName) {
        ReviewImage image = imageRepository.findByImage_url(fileName);
        if(image!= null){
            imageRepository.delete(image);
            bucketService.deleteReviewImage(bucketName, fileName);
        }

        return null;
    }

    @Override
    public List<String> uploadReviewImages(MultipartFile[] files) {
        try {
            List<String> urls = bucketService.uploadMultipleImageFiles(files, bucketName);
            return urls;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
