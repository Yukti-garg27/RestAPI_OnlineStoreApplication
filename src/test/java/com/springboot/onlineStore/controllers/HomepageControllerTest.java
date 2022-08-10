package com.springboot.onlineStore.controllers;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springboot.onlineStore.model.Product;
import com.springboot.onlineStore.model.wrappers.ProductsWrapper;
import com.springboot.onlineStore.services.ProductsService;

/**
 * HomepageController Unit Tests
 */
@ExtendWith(MockitoExtension.class)
public class HomepageControllerTest {
    private static final Product PRODUCT1 = new Product("Color Markers",
            "Four high quality markers for any art project",
            null,
            14.99f,
            "art");
    private static final Product PRODUCT2 = new Product("Desktop Monitor",
            null,
            null,
            10.0f,
            "electronics");

    @Mock
    private ProductsService productsService;

    @InjectMocks
    private HomepageController homepageController;

    @Test
    public void getProductCategories_returnsString() {
        when(productsService.getAllSupportedCategories()).thenReturn(Arrays.asList("category1",
                "category2"));

        String response = homepageController.getProductCategories();

        assertThat(response).isEqualTo("category1,category2");
    }

    @Test
    public void getDealsOfTheDay_success_returnsListOfOneProduct() {
        when(productsService.getDealsOfTheDay(any(Integer.class)))
             .thenReturn(Arrays.asList(PRODUCT1));
        ProductsWrapper productsWrapper = homepageController.getDealsOfTheDay(1);

        assertThat(productsWrapper.getProducts()).containsExactly(PRODUCT1);
    }

    @Test
    public void getProductsForCategory_nullCategory_returnsList() {
        when(productsService.getAllProducts())
         .thenReturn(Arrays.asList(PRODUCT1, PRODUCT2));

        ProductsWrapper productsWrapper = homepageController
        		 .getProductsForCategory(null);

        assertThat(productsWrapper.getProducts())
        .containsExactlyInAnyOrder(PRODUCT2, PRODUCT1);
    }

    @Test
    public void getProductsForCategory_withCategory_returnsList() {
        String category = "electronics";
        when(productsService.getProductsByCategory(eq(category)))
          .thenReturn(Arrays.asList(PRODUCT2));

        ProductsWrapper productsWrapper = homepageController
        		.getProductsForCategory("electronics");

        assertThat(productsWrapper.getProducts()).containsExactly(PRODUCT2);
    }
}
