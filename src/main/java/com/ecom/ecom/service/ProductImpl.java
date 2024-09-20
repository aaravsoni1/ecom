package com.ecom.ecom.service;

import com.ecom.ecom.entity.Category;
import com.ecom.ecom.entity.Product;
import com.ecom.ecom.entity.ProductImage;
import com.ecom.ecom.exception.ResourceNotFoundException;
import com.ecom.ecom.payload.ProductDto;
import com.ecom.ecom.repository.CategoryRepository;
import com.ecom.ecom.repository.ProductImageRepository;
import com.ecom.ecom.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductImageRepository productImageRepository;
    private ProductImageServiceImpl imageService;
    private CategoryRepository categoryRepository;

    public ProductImpl(ProductRepository productRepository, ProductImageRepository productImageRepository, ProductImageServiceImpl imageService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.imageService = imageService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto, MultipartFile [] files) {
        Product entity = DtoToEntity(productDto);
        if (entity.getCategory() == null) {
            Category category = categoryRepository.findById(productDto.getCategory_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + entity.getCategory().getId()));
            entity.setCategory(category); // Assign the persistent category
        } else {
            throw new IllegalArgumentException("Category must be provided for the product");
        }


        // Save the Product entity
        Product savedProduct = productRepository.save(entity);

        // Upload images and associate them with the product
        List<String> urls = imageService.uploadImage(files);

        List<ProductImage> productImages = urls.stream()
                .map(url -> {
                    ProductImage productImage = new ProductImage();
                    productImage.setImage_url(Collections.singletonList(url));
                    productImage.setProduct(savedProduct);
                    return productImage;
                }).collect(Collectors.toList());

        // Save Product Images
        savedProduct.setProductImages(productImages);

        // Save the Product again with the associated images
        Product newProduct = productRepository.save(savedProduct);

        productImageRepository.saveAll(productImages);

        // Convert the saved product back to DTO
        return EntityToDto(newProduct);
    }
    public Product DtoToEntity (ProductDto dto){
        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setStock(dto.getStock());
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
        dto.setCreated_at(entity.getCreated_at());
        dto.setCategory_id(entity.getCategory().getId());
        if (entity.getProductImages() != null) {
            dto.setImg_url(entity.getProductImages().stream()
                    .flatMap(reviewImage -> reviewImage.getImage_url().stream())
                    .collect(Collectors.toList()));
        } else {
            dto.setImg_url(Collections.emptyList()); // Set an empty list if no images
        }
        return dto;
    }

    @Override
    public ProductDto updateProduct(ProductDto product,Long categoryId) {
        Optional<Product> byId = productRepository.findById(product.getId());
        if (byId.isPresent()) {
            Product entity = DtoToEntity(product);
            entity.setUpdated_at(new Date());
            Product saved = productRepository.save(entity);
            return EntityToDto(saved);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto getProductById(Long productId){
        Optional<Product> productById = productRepository.findProductById(productId);
            if(productById.isPresent()) {
                return EntityToDto(productById.get());
            }
            return null;
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List<Product> all = productRepository.findAll();
        List<ProductDto> collect = all.stream().map(p -> EntityToDto(p)).collect(Collectors.toList());
        return collect;
    }

}
