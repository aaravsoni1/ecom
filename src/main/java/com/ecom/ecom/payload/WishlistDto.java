package com.ecom.ecom.payload;

import lombok.Data;

import java.util.Date;

@Data
public class WishlistDto {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDeleted;

}
