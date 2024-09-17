package com.ecom.ecom.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class BucketService {

    @Autowired
    private AmazonS3 amazonS3;

    public String uploadProductImage(MultipartFile file, String bucketName) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        try {
            File conFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            try {
                file.transferTo(conFile);
                String folderPath = "/product_images/";
                String key = folderPath + conFile.getName();
                amazonS3.putObject(bucketName, conFile.getName(), conFile);
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file : " + s3Exception.getMessage();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    public String uploadReviewImage(MultipartFile file, String bucketName) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        try {
            File conFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            try {
                file.transferTo(conFile);
                String folderPath = "review_images/";
                String key = folderPath + conFile.getName();
                amazonS3.putObject(bucketName, key, conFile);
                return amazonS3.getUrl(bucketName, key).toString();
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file : " + s3Exception.getMessage();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    public String deleteReviewImage(String fileName, String bucketName) {
        try {
            String key = extractKeyFromUrl(fileName, bucketName);
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, key);
            amazonS3.deleteObject(deleteObjectRequest);
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }

        return "File " + fileName + " deleted successfully from S3";
    }

    private String extractKeyFromUrl(String fileUrl, String bucketName) {
        try {
            URL url = new URL(fileUrl);
            String path = url.getPath();
            return path.substring(1).replaceFirst(bucketName + "/", "");
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL: " + fileUrl);
        }
    }

    public List<String> uploadMultipleImageFiles(MultipartFile[] files, String bucketName) throws IOException {
        List<String> image_url_list = new ArrayList<String>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null));
            image_url_list.add(amazonS3.getUrl(bucketName, fileName).toString());
        }
        return image_url_list;
    }
}