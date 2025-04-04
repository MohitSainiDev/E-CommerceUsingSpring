package com.ecommerce.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {



	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public ResponseEntity<List<Category>> getAllCategories() {
		return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> createCategory(Category category) {
		categoryRepository.save(category);
		return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<String> deleteCategoryById(long categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);
		if (category.isEmpty())
			return new ResponseEntity<>("Category not found with id:" + categoryId, HttpStatus.NOT_FOUND);
		categoryRepository.deleteById(categoryId);
		return new ResponseEntity<>("Category deleted SuccessFully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateCategory(Category category, long id) {
		Optional<Category> extractedCategory = categoryRepository.findById(id);
		if (extractedCategory.isEmpty())
			return new ResponseEntity<>("Category not found with id:" + id, HttpStatus.NOT_FOUND);
		else {
			Category getCategory = extractedCategory.get();
			getCategory.setCategoryName(category.getCategoryName());
			categoryRepository.save(getCategory);
			return new ResponseEntity<>(extractedCategory, HttpStatus.OK);
		}
	}

}
