package com.ecommerce.project.service;

import org.springframework.http.ResponseEntity;

import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryService {

	ResponseEntity<CategoryResponse> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);

	ResponseEntity<CategoryDTO> createCategory(CategoryDTO categoryDTO);

	ResponseEntity<CategoryDTO> deleteCategory(long categoryId);

	ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO, long id);
}
