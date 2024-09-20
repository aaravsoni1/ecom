package com.ecom.ecom.payload;

import lombok.Data;

import java.util.List;

@Data
public class ProductImageDto {

    private Long ig;
    private List<String> imageUrls;

}
