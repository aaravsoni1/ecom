package com.ecom.ecom.payload;

import lombok.Data;

import java.util.Date;

@Data
public class WishlistDto {
      private Long id;
      private Long userId;
      private Long productId;
      private String productName;
      private Integer productPrice;
}
