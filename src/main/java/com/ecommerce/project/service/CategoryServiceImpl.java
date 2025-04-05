package com.ecommerce.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.exceptions.APIException;
import com.ecommerce.project.model.exceptions.ResourceNotFoundException;
import com.ecommerce.project.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {



	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> extractedCategory = categoryRepository.findAll();
		if (extractedCategory.isEmpty())
			throw new APIException("No Category is Present");
		return new ResponseEntity<>(extractedCategory, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> createCategory(Category category) {
		Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		if (savedCategory != null)
			throw new APIException("Category already Present");
		else
		{
			categoryRepository.save(category);
			return new ResponseEntity<>("Category created SuccessFully", HttpStatus.CREATED);
		}

	}

	@Override
	public ResponseEntity<String> deleteCategory(long categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);
		if (category.isEmpty())
			throw new ResourceNotFoundException("category", "categoryId", categoryId);
		categoryRepository.deleteById(categoryId);
		return new ResponseEntity<>("Category deleted SuccessFully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateCategory(Category category, long categoryId) {
		Optional<Category> extractedCategory = categoryRepository.findById(categoryId);
		if (extractedCategory.isEmpty())
			throw new ResourceNotFoundException("category", "categoryId", categoryId);
		else {
			Category getCategory = extractedCategory.get();
			getCategory.setCategoryName(category.getCategoryName());
			categoryRepository.save(getCategory);
			return new ResponseEntity<>(extractedCategory, HttpStatus.OK);
		}
	}

}
