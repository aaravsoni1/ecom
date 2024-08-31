package com.ecom.ecom.service;

import com.ecom.ecom.entity.Product;
import com.ecom.ecom.payload.ProductDto;
import com.ecom.ecom.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto addProduct(ProductDto product) {
        Product entity = DtoToEntity(product);
        Product saved = productRepository.save(entity);
        return EntityToDto(saved);
    }
    public Product DtoToEntity (ProductDto dto){
        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setStock(dto.getStock());
        entity.setImg_url(dto.getImg_url());
        entity.setCreated_at(new Date());
        return entity;
    }
    public ProductDto EntityToDto(Product entity){
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setStock(entity.getStock());
        dto.setImg_url(entity.getImg_url());
        dto.setCreated_at(entity.getCreated_at());
        return dto;
    }

    @Override
    public ProductDto updateProduct(ProductDto product) {
        Product entity = DtoToEntity(product);
        entity.setUpdated_at(new Date());
        Product saved = productRepository.save(entity);
        return EntityToDto(saved);
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public ProductDto getProductById(Long productId) {
        return null;
    }
}
