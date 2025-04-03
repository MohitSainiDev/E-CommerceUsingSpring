package com.ecommerce.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.project.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	private List<Category> categories = new ArrayList<>();
	private Long id = 1L;
	@Override
	public ResponseEntity<List<Category>> getAllCategories() {
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> createCategory(Category category) {
		category.setCategoryId(id++);
		categories.add(category);
		return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<String> deleteCategoryById(long categoryId) {
		Category category = categories.stream().filter(c -> c.getCategoryId().equals(categoryId)).findFirst()
				.orElse(null);
		if (category == null)
			return new ResponseEntity<>("Category not found with id:" + id, HttpStatus.NOT_FOUND);
		categories.remove(category);
		return new ResponseEntity<>("Category deleted SuccessFully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateCategory(Category category, long id) {
		Category extractedCategory = categories.stream().filter(c -> c.getCategoryId().equals(id)).findFirst()
				.orElse(null);
		if (extractedCategory == null)
			return new ResponseEntity<>("Category not found with id:" + id, HttpStatus.NOT_FOUND);
		else {
			extractedCategory.setCategoryName(category.getCategoryName());

			return new ResponseEntity<>(extractedCategory, HttpStatus.OK);
		}
	}

}
