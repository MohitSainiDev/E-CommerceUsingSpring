package com.ecommerce.project.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {

	ProductDTO addProduct(ProductDTO productDTO, Long cateogryId);

	ProductResponse getAllProducts();

	ProductResponse searchByCategory(Long categoryId);

	ProductResponse searchProductsByKeyword(String keyword);

	ProductDTO updateProduct(Long productId, ProductDTO productDTO);

	ProductDTO deleteProduct(Long productId);

	ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

}
