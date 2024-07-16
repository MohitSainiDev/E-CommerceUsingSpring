package com.ecommerce.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDTO addProduct(Long categoryId, Product product) {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
			product.setCategory(category);
			double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
			product.setSpecialPrice(specialPrice);
			product.setImage("Default.png");
			Product savedProduct = productRepository.save(product);
			return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductResponse getAllProducts() {
		List<Product> products=productRepository.findAll();
		List<ProductDTO> productsDTO = products.stream().map(product->modelMapper
				.map(product, ProductDTO.class)).collect(Collectors.toList());
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productsDTO);
		return productResponse;
		
	}

	@Override
	public ProductResponse searchByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
		List<ProductDTO> productsDTO = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.collect(Collectors.toList());
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productsDTO);
		return productResponse;
	}

	@Override
	public ProductResponse searchProductByKeyWord(String keyWord) {
		List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyWord + '%');
		List<ProductDTO> productsDTO = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.collect(Collectors.toList());
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productsDTO);
		return productResponse;
	}

	@Override
	public ProductDTO updateProduct(Long productId, Product product) {
		Product ProductFromDb = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", productId));
		ProductFromDb.setProductName(product.getProductName());
		ProductFromDb.setDescription(product.getDescription());
		ProductFromDb.setQuantity(product.getQuantity());
		ProductFromDb.setDiscount(product.getDiscount());
		ProductFromDb.setPrice(product.getPrice());
		ProductFromDb.setSpecialPrice(product.getSpecialPrice());

		Product savedProduct = productRepository.save(ProductFromDb);
		return modelMapper.map(savedProduct, ProductDTO.class);

	}

	@Override
	public ProductDTO deleteProduct(Long productId) {
		Product ProductFromDb = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", productId));
		productRepository.delete(ProductFromDb);
		return modelMapper.map(ProductFromDb, ProductDTO.class);

	}

}
