package com.ecom.ecom.payload;

import lombok.Data;

import java.util.Date;
@Data
public class ReviewDto {
    private Long id;
    private Float rating;
    private String comment;
    private String image_url;
    private Date created_at;
    private Date updated_at;
    private Long user_id;
    private Long product_id;
}
