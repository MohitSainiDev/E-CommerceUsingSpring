package com.ecommerce.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

//	private List<Category> categories = new ArrayList<>();

//	private long nextId = 1L;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		if (categories.isEmpty())
			throw new APIException("No category created till now");
		return categoryRepository.findAll();
	}

	@Override
	public void createCategory(Category category) {
		Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		if (savedCategory != null)
			throw new APIException("category with the name " + category.getCategoryName() + " already exists");
//		category.setCategoryId(nextId++);
		categoryRepository.save(category);
	}

	@Override
	public String deleteCategory(Long categoryId) {
		
		Optional<Category> category1 = categoryRepository.findById(categoryId);
		Category savedCategory = category1
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		categoryRepository.delete(savedCategory);
		return "Category with categoryId " + categoryId + " deleted successfully";
//		List<Category> categories = categoryRepository.findAll();
//		for(Category c:categories)
//		{
//			if(c.getCategoryId()==categoryId)
//			{
//				categoryRepository.delete(c);
//				return "Cateogry with categoryId " + categoryId + " deleted successfully";
//
//			}
//		}
//		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
//		
	}

	@Override
	public String updateCategory(Category category, Long categoryId) {
		Optional<Category> category1 = categoryRepository.findById(categoryId);
		Category savedCategory = category1
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		savedCategory.setCategoryName(category.getCategoryName());
		categoryRepository.save(savedCategory);
		return "Category updated successfully";
		// for (Category c : categories) {
//
//			if (c.getCategoryId() == categoryId) {
//				c.setCategoryName(category.getCategoryName());
//				categoryRepository.save(c);
//				return "Category updated successfully";
//
//			}
//		}
//		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");

	}

}
