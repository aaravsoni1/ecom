package com.ecom.ecom.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class BucketService {

    @Autowired
    private AmazonS3 amazonS3;

    public String uploadProductImage(MultipartFile file, String bucketName){
        if(file.isEmpty()){
            throw new IllegalStateException("Cannot upload empty file");
        }
        try{
            File conFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            try {
                file.transferTo(conFile);
                String folderPath = "product_images/";
                String key = folderPath + conFile.getName();
                amazonS3.putObject(bucketName, key, conFile);
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file : " + s3Exception.getMessage();
            }
        }catch(Exception e) {
            throw new IllegalStateException("Failed to upload the file",e);
        }
    }

    public Object uploadReviewImage(MultipartFile file, String bucketName) {
        if(file.isEmpty()){
            throw new IllegalStateException("Cannot upload empty file");
        }
        try{
            File conFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            try {
                file.transferTo(conFile);
                String folderPath = "review_images/";
                String key = folderPath + conFile.getName();
                amazonS3.putObject(bucketName, key, conFile);
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file : " + s3Exception.getMessage();
            }
        }catch(Exception e) {
            throw new IllegalStateException("Failed to upload the file",e);
        }
    }
}
