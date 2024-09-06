package com.ecom.ecom.payload;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {

    private long id;

    private Integer total_price;

    private String status;

    private Date createdAt;

    private Date updatedAt;


}
