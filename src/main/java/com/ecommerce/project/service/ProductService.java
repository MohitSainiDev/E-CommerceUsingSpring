package com.ecommerce.project.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {

	ProductDTO addProduct(ProductDTO productDTO, Long cateogryId);

	ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder);

	ProductResponse searchByCategory(Long categoryId, int pageNumber, int pageSize, String sortBy, String sortOrder);

	ProductResponse searchProductsByKeyword(String keyword, int pageNumber, int pageSize, String sortBy,
			String sortOrder);

	ProductDTO updateProduct(Long productId, ProductDTO productDTO);

	ProductDTO deleteProduct(Long productId);

	ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

	

}
