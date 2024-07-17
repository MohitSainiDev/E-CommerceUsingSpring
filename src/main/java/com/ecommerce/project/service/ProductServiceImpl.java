package com.ecommerce.project.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.exceptions.APIException;
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
	@Autowired
	FileService fileService;

	@Value("${project.image}")
	private String path;

	@Override
	public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
		Product product = modelMapper.map(productDTO, Product.class);
		List<Product> products=productRepository.findAll();
		for (Product pro : products)
		{
			if (pro.getProductName().equals(productDTO.getProductName()) && pro.getCategory().equals(category))
					throw new APIException(" Product already present");
					
		}
				
		product.setCategory(category);
			double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
			product.setSpecialPrice(specialPrice);
			product.setImage("Default.png");
			Product savedProduct = productRepository.save(product);
			return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Product> productPage = productRepository.findAll(pageDetails);
		List<Product> products = productPage.getContent();
		List<ProductDTO> productsDTO = products.stream().map(product->modelMapper
				.map(product, ProductDTO.class)).collect(Collectors.toList());

		if (products.isEmpty())
			throw new APIException("No Products present");
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productsDTO);
		productResponse.setPageNumber(productPage.getNumber());
		productResponse.setPageSize(productPage.getSize());
		productResponse.setTotalElements(productPage.getTotalElements());
		productResponse.setTotalPages(productPage.getTotalPages());
		productResponse.setLastPage(productPage.isLast());
		return productResponse;
		
	}

	@Override
	public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
			String sortOrder) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category, pageDetails);
		List<Product> products = productPage.getContent();

		// List<Product> products =
		// productRepository.findByCategoryOrderByPriceAsc(category);
		List<ProductDTO> productsDTO = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.collect(Collectors.toList());
		if (products.isEmpty())
			throw new APIException("No Products present");
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productsDTO);
		productResponse.setPageNumber(productPage.getNumber());
		productResponse.setPageSize(productPage.getSize());
		productResponse.setTotalElements(productPage.getTotalElements());
		productResponse.setTotalPages(productPage.getTotalPages());
		productResponse.setLastPage(productPage.isLast());
		return productResponse;
	}

	@Override
	public ProductResponse searchProductByKeyWord(String keyWord, Integer pageNumber, Integer pageSize, String sortBy,
			String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%' + keyWord + '%', pageDetails);

		List<Product> products = productPage.getContent();
		// List<Product> products =
		// productRepository.findByProductNameLikeIgnoreCase('%' + keyWord + '%');
		List<ProductDTO> productsDTO = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.collect(Collectors.toList());
		if (products.isEmpty())
			throw new APIException("No Products present");
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productsDTO);
		productResponse.setPageNumber(productPage.getNumber());
		productResponse.setPageSize(productPage.getSize());
		productResponse.setTotalElements(productPage.getTotalElements());
		productResponse.setTotalPages(productPage.getTotalPages());
		productResponse.setLastPage(productPage.isLast());
		return productResponse;
	}

	@Override
	public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
		Product ProductFromDb = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
		Product product = modelMapper.map(productDTO, Product.class);
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
				.orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
		productRepository.delete(ProductFromDb);
		return modelMapper.map(ProductFromDb, ProductDTO.class);

	}

	@Override
	public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
		Product ProductFromDb = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));


		String fileName = fileService.uploadImage(path, image);
		ProductFromDb.setImage(fileName);
		Product updatedProduct = productRepository.save(ProductFromDb);

		return modelMapper.map(updatedProduct, ProductDTO.class);
	}


}
