package com.ecom.ecom.controller;

import com.ecom.ecom.payload.CategoryDto;
import com.ecom.ecom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDto dto){
        CategoryDto categoryDto = categoryService.addCategory(dto);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if (categoryDto!=null) {
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        }
        return new ResponseEntity<>("No category Found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<CategoryDto>> getAllCategories(){
        List<CategoryDto> allCategories = categoryService.getAllCategories();
       return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategoryById(@PathVariable Long id, @RequestBody CategoryDto dto){
        CategoryDto updatedCategoryDto = categoryService.updateCategory(id, dto);
        return new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        boolean category = categoryService.deleteCategory(id);
        if (category == true) {
            return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
    }
}
