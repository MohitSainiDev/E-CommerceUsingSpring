package com.ecommerce.project.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.project.model.Category;

public interface CategoryService {

	ResponseEntity<List<Category>> getAllCategories();

	ResponseEntity<String> createCategory(Category category);

	ResponseEntity<String> deleteCategoryById(long categoryId);

	ResponseEntity<?> updateCategory(Category category, long id);
}
