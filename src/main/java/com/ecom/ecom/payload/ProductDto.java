package com.ecom.ecom.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String stock;
    private Integer price;
    private List<String> img_url;
    private Date created_at;
    private Date updated_at;
    private Long category_id;
}
