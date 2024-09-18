package com.ecom.ecom.payload;

import lombok.Data;

import java.util.List;

@Data
public class ReviewImageDto {

    private Long id;
    private List<String> imageUrls;
    private Long reviewId;
}
