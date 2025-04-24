package com.ecommerce.project.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping("/admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO,
			@PathVariable Long categoryId) {

		ProductDTO savedproductDTO = productService.addProduct(productDTO, categoryId);
		return new ResponseEntity<>(savedproductDTO, HttpStatus.CREATED);
	}

	@GetMapping("/public/products")
	public ResponseEntity<ProductResponse> getAllProducts(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
		ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@GetMapping("/public/categories/{categoryId}")
	public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
		ProductResponse productResponse = productService.searchByCategory(categoryId, pageNumber, pageSize, sortBy,
				sortOrder);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@GetMapping("/public/products/keyword/{keyword}")
	public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
		ProductResponse productResponse = productService.searchProductsByKeyword(keyword, pageNumber, pageSize, sortBy,
				sortOrder);
		return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
	}

	@PutMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,
			@PathVariable Long productId) {
		ProductDTO savedproductDTO = productService.updateProduct(productId, productDTO);
		return new ResponseEntity<>(savedproductDTO, HttpStatus.OK);
	}

	@DeleteMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
		ProductDTO productDTO = productService.deleteProduct(productId);
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}

	@PutMapping("/products/{productId}/image")
	public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
			@RequestParam("image") MultipartFile image) throws IOException {
		ProductDTO productDTO = productService.updateProductImage(productId, image);
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}

}
