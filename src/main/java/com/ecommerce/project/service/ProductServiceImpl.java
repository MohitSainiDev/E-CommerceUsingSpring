package com.ecommerce.project.service;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.model.exceptions.APIException;
import com.ecommerce.project.model.exceptions.ResourceNotFoundException;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	FileService fileService;

	@Value("${project.image}")
	private String path;


	@Override
	public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		List<Product> products = category.getProducts();
		
		for(Product p :products)
		{
			if(p.getProductName().equals(productDTO.getProductName()))
				throw new APIException("Product already present!!");
		}
		Product product = modelMapper.map(productDTO, Product.class);
		

		product.setCategory(category);
		double specialPrice = ((100 - product.getDiscount()) * product.getPrice()) * 0.01;
		product.setImage("default.png");
		product.setSpecialPrice(specialPrice);
		Product savedProduct = productRepository.save(product);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductResponse getAllProducts() {
		List<Product> products = productRepository.findAll();
		if (products.size() == 0)
			throw new APIException("No Product is present!!");
		List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();

		ProductResponse productResponse = new ProductResponse();

		productResponse.setContent(productDTOs);
		return productResponse;
	}

	@Override
	public ProductResponse searchByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
		List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();

		ProductResponse productResponse = new ProductResponse();

		productResponse.setContent(productDTOs);
		return productResponse;
		
	}

	@Override
	public ProductResponse searchProductsByKeyword(String keyword) {
		List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
		List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();

		ProductResponse productResponse = new ProductResponse();

		productResponse.setContent(productDTOs);
		return productResponse;
	}

	@Override
	public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
		Product extractedproduct = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		Product product = modelMapper.map(productDTO, Product.class);
		
		extractedproduct.setProductName(product.getProductName());
		extractedproduct.setDescription(product.getDescription());
		extractedproduct.setQuantity(product.getQuantity());
		extractedproduct.setDiscount(product.getDiscount());
		extractedproduct.setPrice(product.getPrice());
		extractedproduct.setImage("default.png");
		extractedproduct.setSpecialPrice(product.getSpecialPrice());
		Product savedProduct = productRepository.save(extractedproduct);
		return modelMapper.map(savedProduct, ProductDTO.class);

	}

	@Override
	public ProductDTO deleteProduct(Long productId) {
		Product extractedproduct = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		productRepository.delete(extractedproduct);
		return modelMapper.map(extractedproduct, ProductDTO.class);
	}

	@Override
	public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {

		Product productFromDb = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		// upload image to server
		// get the file name of uploaded image
		String fileName = fileService.uploadImage(path, image);

		// updating the new file name to the product
		productFromDb.setImage(fileName);

		// save updated product

		Product updatedProduct = productRepository.save(productFromDb);

		// return DTO after mapping product to DTO

		return modelMapper.map(updatedProduct, ProductDTO.class);

	}



}
