package com.ecommerce.project.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping("/admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product, @PathVariable Long categoryId) {
		ProductDTO productDTO = productService.addProduct(categoryId, product);
		return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
	}

	@GetMapping("/public/products")
	public ResponseEntity<ProductResponse> getAllProducts() {
		ProductResponse productResponse = productService.getAllProducts();
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@GetMapping("/public/categories/{categoryId}/products")
	public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
		ProductResponse productResponse = productService.searchByCategory(categoryId);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@GetMapping("/public/products/keyword/{keyWord}")
	public ResponseEntity<ProductResponse> getProductsByKeyWord(@PathVariable String keyWord) {
		ProductResponse productResponse = productService.searchProductByKeyWord(keyWord);
		return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
	}

	@PutMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody Product product, @PathVariable Long productId) {
		ProductDTO updatedProductDTO = productService.updateProduct(productId, product);
		return new ResponseEntity<>(updatedProductDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDTO> DeleteProduct(@PathVariable Long productId) {
		ProductDTO deletedProductDTO = productService.deleteProduct(productId);
		return new ResponseEntity<>(deletedProductDTO, HttpStatus.OK);
	}

}
