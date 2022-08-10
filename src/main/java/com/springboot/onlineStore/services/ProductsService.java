package com.springboot.onlineStore.services;

import com.springboot.onlineStore.model.Product;
import com.springboot.onlineStore.repositories.ProductCategoryRepository;
import com.springboot.onlineStore.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Integrates with the database API and handles products and categories business
 * logic
 */
@Service
public class ProductsService {

	private final ProductCategoryRepository productCategoryRepository;
	private final ProductRepository productRepository;

	public ProductsService(ProductCategoryRepository productCategoryRepository,
			ProductRepository productRepository) {
		this.productCategoryRepository = productCategoryRepository;
		this.productRepository=productRepository;

	}

	public List<String> getAllSupportedCategories() {

		return productCategoryRepository.findAll().stream()
				.map(productCategory -> productCategory.getCategory())
				.collect(Collectors.toList());

	}
	
	public List<Product>  getDealsOfTheDay(int number_of_products){
		return productRepository.findAtMostNumberOfProducts(number_of_products);
	}

	public List<Product> getProductsByCategory(String productCategory){
		return productRepository.findByCategory(productCategory);
	}
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Product getProductById(long id){
		return productRepository.findById( id)
				.orElseThrow(()->
				new IllegalStateException("Product does not exist with id: "+id));
				
	}
}
