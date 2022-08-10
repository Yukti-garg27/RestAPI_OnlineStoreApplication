package com.springboot.onlineStore.controllers;

import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.onlineStore.model.wrappers.ProductsWrapper;
import com.springboot.onlineStore.services.ProductsService;

/**
 * Homepage REST controller
 */
@RestController
public class HomepageController {
  
	private final ProductsService productsService;
	
	public HomepageController(ProductsService productsService) {
		this.productsService=productsService;
	}
	
	@GetMapping("/categories")
	public String getProductCategories() {
	   return productsService.getAllSupportedCategories()
			   .stream()
			   .collect(Collectors.joining(", "));
	}
	
	@GetMapping("/deals_of_the_day/{number_of_products}")
	public ProductsWrapper getDealsOfTheDay(@PathVariable("number_of_products")
	                    int number_of_products) {
	return new ProductsWrapper(productsService.getDealsOfTheDay(number_of_products));
	}
	@GetMapping("/products")
	public ProductsWrapper getProductsForCategory(
			@RequestParam(name="category", required=false) String category) {
		
		if(category!=null && !category.isEmpty())
		return new ProductsWrapper(productsService.getProductsByCategory(category));
		
		return new ProductsWrapper(productsService.getAllProducts());
	}
	
}
