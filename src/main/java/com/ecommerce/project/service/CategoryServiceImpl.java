package com.ecommerce.project.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.exceptions.APIException;
import com.ecommerce.project.model.exceptions.ResourceNotFoundException;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {



	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CategoryResponse categoryResponse;

	@Override
	public ResponseEntity<CategoryResponse> getAllCategories(int pageNumber, int pageSize, String sortBy,
			String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
		List<Category> extractedCategory = categoryPage.getContent();
		if (extractedCategory.isEmpty())
			throw new APIException("No Category is Present");

		List<CategoryDTO> categoryDTOs = extractedCategory.stream()
				.map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
		categoryResponse.setContent(categoryDTOs);
		categoryResponse.setPageNumber(categoryPage.getNumber());
		categoryResponse.setPageSize(categoryPage.getSize());
		categoryResponse.setTotalElements(categoryPage.getTotalElements());
		categoryResponse.setTotalPages(categoryPage.getTotalPages());
		categoryResponse.setLastpage(categoryPage.isLast());

		return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CategoryDTO> createCategory(CategoryDTO categoryDTO) {

		Category category = modelMapper.map(categoryDTO, Category.class);
		Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		if (savedCategory != null)
			throw new APIException("Category already Present");
		Category finalsavedCategory = categoryRepository.save(category);
		CategoryDTO savedCategoryDTO = modelMapper.map(finalsavedCategory, CategoryDTO.class);
		return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);


	}

	@Override
	public ResponseEntity<CategoryDTO> deleteCategory(long categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);
		if (category.isEmpty())
			throw new ResourceNotFoundException("category", "categoryId", categoryId);
		categoryRepository.deleteById(categoryId);
		Category finalDeletedCategory = category.get();
		CategoryDTO savedCategoryDTO = modelMapper.map(finalDeletedCategory, CategoryDTO.class);

		return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO, long categoryId) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		Optional<Category> extractedCategory = categoryRepository.findById(categoryId);
		if (extractedCategory.isEmpty())
			throw new ResourceNotFoundException("category", "categoryId", categoryId);

			Category getCategory = extractedCategory.get();
			getCategory.setCategoryName(category.getCategoryName());
			Category finalsavedCategory = categoryRepository.save(getCategory);
			CategoryDTO savedCategoryDTO = modelMapper.map(finalsavedCategory, CategoryDTO.class);
			return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);

	}

}
