package com.springboot.onlineStore.controllers;


import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.springboot.onlineStore.model.Product;
import com.springboot.onlineStore.repositories.ProductCategoryRepository;
import com.springboot.onlineStore.repositories.ProductRepository;
import com.springboot.onlineStore.services.ProductsService;

/**
 * HomepageController Integration Tests
 */
@WebMvcTest(HomepageController.class)
public class HomepageControllerIT {
    private static final Product ELECTRONICS_PRODUCT1 = new Product("Apple Laptop",
            null,
            null,
            5.5f,
            "electronics");
    private static final Product ELECTRONICS_PRODUCT2 = new Product("Desktop Monitor",
            null,
            null,
            10.0f,
            "electronics");
    private static final Product ART_PRODUCT = new Product("Color Markers",
            "Four high quality markers for any art project",
            "markers_640x426.jpeg",
            14.99f,
            "art");

    @MockBean
    private ProductCategoryRepository productCategoryRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ProductsService productsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCategories_returnsListOfCategories() throws Exception {
        List<String> expectedCategories = Arrays.asList("apparel", "electronics");
        when(productsService.getAllSupportedCategories())
                .thenReturn(expectedCategories);

        mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("apparel,electronics"));
    }

    @Test
    public void getDealsOfTheDay_withPathParameter_returnsJsonResponse() throws Exception {
        List<Product> products = Arrays.asList(
                ELECTRONICS_PRODUCT1,
                ELECTRONICS_PRODUCT2);
        when(productsService.getDealsOfTheDay(2))
                .thenReturn(products);

        mockMvc.perform(get("/deals_of_the_day/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products").isNotEmpty())
                .andExpect(jsonPath("$.products[0].name").value("Apple Laptop"))
                .andExpect(jsonPath("$.products[0].priceUSD")
                		.value(equalTo(5.5f), Float.class))
                .andExpect(jsonPath("$.products[1].name").value("Desktop Monitor"))
                .andExpect(jsonPath("$.products[1].priceUSD")
                		.value(equalTo(10.0f), Float.class));
    }

    @Test
    public void getProducts_withoutCategories_returnsAllProducts() throws Exception {
        List<Product> allProducts = Arrays.asList(ELECTRONICS_PRODUCT1,
                ELECTRONICS_PRODUCT2,
                ART_PRODUCT);
        when(productsService.getAllProducts())
                .thenReturn(allProducts);

        // Act and Assert
        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.products.length()").value(3));
    }
}
