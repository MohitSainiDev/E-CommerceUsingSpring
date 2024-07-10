package com.ecommerce.project.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;

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
//	@RequestMapping(value = "/public/categories", method = RequestMethod.GET)
		public ResponseEntity<List<Category>> getAllCategories() {
			List<Category> allCategories = categoryService.getAllCategories();
			return new ResponseEntity<>(allCategories, HttpStatus.OK);
		}

		@PostMapping("/public/categories")
//		@RequestMapping(value = "/public/categories", method = RequestMethod.POST)
		public ResponseEntity<String> createCatgory(@RequestBody Category category) {
			categoryService.createCategory(category);
			return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
		}

		@DeleteMapping("/admin/categories/{categoryId}")
		public ResponseEntity<String> deleteCatgory(@PathVariable Long categoryId) {
			try {
				String status = categoryService.deleteCategory(categoryId);
				return new ResponseEntity<>(status, HttpStatus.OK);
			} catch (ResponseStatusException e) {
				return new ResponseEntity<>(e.getReason(), e.getStatusCode());
			}
		}

		@PutMapping("/public/categories/{categoryId}")
		public ResponseEntity<String> updateCatgory(@RequestBody Category category, @PathVariable Long categoryId) {
			try {
				categoryService.updateCategory(category, categoryId);
				return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
			} catch (ResponseStatusException e) {
				return new ResponseEntity<>(e.getReason(), e.getStatusCode());
			}
		}
}
