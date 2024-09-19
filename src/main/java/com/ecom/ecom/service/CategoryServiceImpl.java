package com.ecom.ecom.service;

import com.ecom.ecom.entity.Category;
import com.ecom.ecom.payload.CategoryDto;
import com.ecom.ecom.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto addCategory(CategoryDto dto) {
        try {
            Category category = DtoToEntity(dto);
            Category saved = categoryRepository.save(category);
            return EntityToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Convert Entity to DTO
    private CategoryDto EntityToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    // Convert DTO to Entity
    private Category DtoToEntity(CategoryDto dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto dto) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isPresent()){
            Category category = DtoToEntity(dto);
            return EntityToDto(category);
        }
        return null;
    }

    @Override
    public boolean deleteCategory(Long CategoryId) {
        if (categoryRepository.existsById(CategoryId)) {
            categoryRepository.deleteById(CategoryId);
            return true;
        }
        return false;
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isPresent()){
            return EntityToDto(optionalCategory.get());
        }
        return null;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> all = categoryRepository.findAll();
        List<CategoryDto> collect = all.stream().map(c -> EntityToDto(c)).collect(Collectors.toList());
        return collect;
    }
}
