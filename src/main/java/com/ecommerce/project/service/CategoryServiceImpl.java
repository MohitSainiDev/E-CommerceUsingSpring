package com.ecommerce.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.project.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	private List<Category> categories = new ArrayList<>();

	private long nextId = 1L;

	@Override
	public List<Category> getAllCategories() {
		return categories;
	}

	@Override
	public void createCategory(Category category) {
		category.setCategoryId(nextId++);
		categories.add(category);
	}

	@Override
	public String deleteCategory(Long categoryId) {
		for(Category c:categories)
		{
			if(c.getCategoryId()==categoryId)
			{
				categories.remove(c);
				return "Cateogry with categoryId " + categoryId + " deleted successfully";

			}
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
		
	}

	@Override
	public String updateCategory(Category category, Long categoryId) {
		for (Category c : categories) {
			if (c.getCategoryId() == categoryId) {
				c.setCategoryName(category.getCategoryName());
				return "Category updated successfully";

			}
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");

	}

}
