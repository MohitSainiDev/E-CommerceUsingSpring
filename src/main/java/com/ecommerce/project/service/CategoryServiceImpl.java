package com.ecommerce.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {



	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public ResponseEntity<List<Category>> getAllCategories() {
		return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> createCategory(Category category) {
		categoryRepository.save(category);
			return new ResponseEntity<String>("Created successfully", HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<String> deleteCategoryById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		Category savedCategory = category
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
		
		categoryRepository.delete(savedCategory);
				return new ResponseEntity<String>("Category Deleted Successfully", HttpStatus.OK);


	}

	@Override
	public ResponseEntity<String> updateCategory(Category updatedCategory, Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		Category savedCategory=category.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));
		
		savedCategory.setCategoryName(updatedCategory.getCategoryName());
		categoryRepository.save(savedCategory);
				return new ResponseEntity<String>("Category Update Successfully", HttpStatus.OK);
			}
		
}


