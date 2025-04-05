package com.ecommerce.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("/public/categories")
	public ResponseEntity<List<Category>> getAllCategories() {
		return categoryService.getAllCategories();
	}

	@PostMapping("/public/categories")
	public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
		return categoryService.createCategory(category);
	}

	@DeleteMapping("/admin/categories/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable long id) {
		return categoryService.deleteCategory(id);
	}

	@PutMapping("/public/categories/{id}")
	public ResponseEntity<?> updateCategory(@Valid @RequestBody Category category, @PathVariable long id) {
		return categoryService.updateCategory(category, id);
	}

}
