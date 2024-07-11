package com.ecommerce.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

//	private List<Category> categories = new ArrayList<>();

	private long nextId = 1L;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public void createCategory(Category category) {
		category.setCategoryId(nextId++);
		categoryRepository.save(category);
	}

	@Override
	public String deleteCategory(Long categoryId) {
		
		Optional<Category> category1 = categoryRepository.findById(categoryId);
		Category savedCategory = category1
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
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
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
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
