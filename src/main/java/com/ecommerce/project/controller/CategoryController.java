package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


	private CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@GetMapping("/public/categories")
	public ResponseEntity<CategoryResponse> getAllCategories(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortOrder) {
		CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
		}

		@PostMapping("/public/categories")
		public ResponseEntity<CategoryDTO> createCatgory(@Valid @RequestBody CategoryDTO categoryDTO) {
			CategoryDTO savedcategoryDTO = categoryService.createCategory(categoryDTO);
			return new ResponseEntity<>(savedcategoryDTO, HttpStatus.CREATED);
		}

		@DeleteMapping("/admin/categories/{categoryId}")
		public ResponseEntity<CategoryDTO> deleteCatgory(@PathVariable Long categoryId) {

			CategoryDTO savedcategoryDTO = categoryService.deleteCategory(categoryId);
			return new ResponseEntity<>(savedcategoryDTO, HttpStatus.OK);

		}

		@PutMapping("/public/categories/{categoryId}")
		public ResponseEntity<CategoryDTO> updateCatgory(@Valid @RequestBody CategoryDTO categoryDTO,
				@PathVariable Long categoryId) {

			CategoryDTO savedcategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
			return new ResponseEntity<>(savedcategoryDTO, HttpStatus.OK);

		}
}