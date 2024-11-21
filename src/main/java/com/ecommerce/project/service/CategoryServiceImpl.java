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
	private CategoryRepository categoryRepository;

	@Override
	public ResponseEntity<List<Category>> getAllCategories() {
		if (categoryRepository.findAll().size() == 0)
			throw new APIException("No category created till now");
		return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> createCategory(Category category) {
		Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		if (savedCategory != null)
			throw new APIException("Category with name " + category.getCategoryName() + " already exists");
		categoryRepository.save(category);
			return new ResponseEntity<String>("Created successfully", HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<String> deleteCategoryById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		Category savedCategory = category
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));
		
		categoryRepository.delete(savedCategory);
				return new ResponseEntity<String>("Category Deleted Successfully", HttpStatus.OK);


	}

	@Override
	public ResponseEntity<String> updateCategory(Category updatedCategory, Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		Category savedCategory = category
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));
		
		savedCategory.setCategoryName(updatedCategory.getCategoryName());
		categoryRepository.save(savedCategory);
				return new ResponseEntity<String>("Category Update Successfully", HttpStatus.OK);
			}
		
}


