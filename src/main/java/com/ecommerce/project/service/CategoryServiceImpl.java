package com.ecommerce.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.project.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	List<Category> categories = new ArrayList<>();
	private Long id = 1L;

	@Override
	public ResponseEntity<List<Category>> getAllCategories() {
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> createCategory(Category category) {
		category.setCategoryId(id++);
			categories.add(category);
			return new ResponseEntity<String>("Created successfully", HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<String> deleteCategoryById(Long id) {
		for (Category category : categories) {

			if (category.getCategoryId().equals(id))
			{
				categories.remove(category);
				return new ResponseEntity<String>("Category Deleted Successfully", HttpStatus.OK);
			}
		}

		return new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);

	}

	@Override
	public ResponseEntity<String> updateCategory(Category updatedCategory, Long id) {

		for (Category category : categories) {

			if (category.getCategoryId().equals(id)) {
				category.setCategoryName(updatedCategory.getCategoryName());
				return new ResponseEntity<String>("Category Update Successfully", HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);

	}
}

