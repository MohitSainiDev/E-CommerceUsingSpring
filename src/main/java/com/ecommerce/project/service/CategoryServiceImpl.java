package com.ecommerce.project.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;


	@Autowired
	private CategoryResponse categoryResponse;

	@Override
	public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		
		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
		List<Category> categories = categoryPage.getContent();
		if (categories.isEmpty())
			throw new APIException("No category created till now");

		List<CategoryDTO> categoryDTOS = categories.stream()
				.map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
		categoryResponse.setContent(categoryDTOS);
		categoryResponse.setPageNumber(categoryPage.getNumber());
		categoryResponse.setPageSize(categoryPage.getSize());
		categoryResponse.setTotalElements(categoryPage.getTotalElements());
		categoryResponse.setTotalPages(categoryPage.getTotalPages());
		categoryResponse.setLastPage(categoryPage.isLast());
		return categoryResponse;

	}

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		Category cateogryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
		if (cateogryFromDb != null)
			throw new APIException("category with the name " + category.getCategoryName() + " already exists");
		Category savedCategory = categoryRepository.save(category);
		CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
		return savedCategoryDTO;

	}

	@Override
	public CategoryDTO deleteCategory(Long categoryId) {
		
		Optional<Category> category1 = categoryRepository.findById(categoryId);
		Category cateogryFromDb = category1
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		categoryRepository.delete(cateogryFromDb);
		CategoryDTO savedCategoryDTO = modelMapper.map(cateogryFromDb, CategoryDTO.class);
		return savedCategoryDTO;

	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		Optional<Category> category1 = categoryRepository.findById(categoryId);
		Category cateogryFromDb = category1
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		cateogryFromDb.setCategoryName(category.getCategoryName());
		Category savedCategory = categoryRepository.save(cateogryFromDb);
		CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
		return savedCategoryDTO;

	}

}
