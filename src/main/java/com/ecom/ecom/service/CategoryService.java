package com.ecom.ecom.service;

import com.ecom.ecom.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    public CategoryDto addCategory(CategoryDto dto);
    public CategoryDto updateCategory(Long categoryId, CategoryDto dto);
    public boolean deleteCategory(Long CategoryId);
    public CategoryDto getCategoryById(Long categoryId);
    public List<CategoryDto> getAllCategories();

}
