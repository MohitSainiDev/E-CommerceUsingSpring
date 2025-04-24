package com.ecommerce.project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findByCategoryOrderByPriceAsc(Pageable pageDetails, Category category);

	Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);

}
