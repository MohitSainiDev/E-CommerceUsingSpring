package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("/public/categories")
	public ResponseEntity<CategoryResponse> getAllCategories(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder) {
		return categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
	}

	@PostMapping("/public/categories")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		return categoryService.createCategory(categoryDTO);
	}

	@DeleteMapping("/admin/categories/{id}")
	public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable long id) {
		return categoryService.deleteCategory(id);
	}

	@PutMapping("/public/categories/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
			@PathVariable long id) {
		return categoryService.updateCategory(categoryDTO, id);
	}


}

