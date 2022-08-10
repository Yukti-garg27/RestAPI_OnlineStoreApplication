package com.springboot.onlineStore.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springboot.onlineStore.model.Product;
import com.springboot.onlineStore.model.ProductCategory;
import com.springboot.onlineStore.repositories.ProductCategoryRepository;
import com.springboot.onlineStore.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {
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
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductsService productsService;

    @Test
    public void getAllSupportedCategories_returnsExpectedCategories() {
        String category1 = "electronics";
        String category2 = "art";
        List<ProductCategory> categories =
                Arrays.asList(new ProductCategory(category1), new ProductCategory(category2));
        when(productCategoryRepository.findAll())
                .thenReturn(categories);

        List<String> supportedCategories = productsService.getAllSupportedCategories();

        assertThat(supportedCategories).containsExactly(category1, category2);
    }

    @Test
    public void getDealsOfTheDay_withLimit_returnsExpectedProducts() {
        when(productRepository.findAtMostNumberOfProducts(eq(2)))
                .thenReturn(Arrays.asList(PRODUCT1, PRODUCT2));

        List<Product> dealsOfTheDay = productsService.getDealsOfTheDay(2);

        assertThat(dealsOfTheDay).containsExactly(PRODUCT1, PRODUCT2);
    }

    @Test
    public void getProductsByCategory_withExistingCategoryProducts_returnsExpectedProducts() {
        when(productRepository.findByCategory(PRODUCT1.getCategory()))
                .thenReturn(Arrays.asList(PRODUCT1));

        List<Product> products = productsService.getProductsByCategory(PRODUCT1.getCategory());

        assertThat(products).containsExactly(PRODUCT1);
    }

    @Test
    public void getAllProducts_returnsAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(PRODUCT1, PRODUCT2));

        List<Product> products = productsService.getAllProducts();

        assertThat(products).containsExactly(PRODUCT1, PRODUCT2);
    }

    @Test
    public void getProductById_success_returnsList() {
        long id = 123;
        when(productRepository.findById(eq(id)))
                .thenReturn(Optional.of(PRODUCT1));

        Product product = productsService.getProductById(id);

        assertThat(product).isEqualTo(PRODUCT1);
    }

    @Test
    public void getProductById_nonExistingProduct_throwsException() {
        long id = 123;
        when(productRepository.findById(eq(id)))
                .thenReturn(Optional.empty());

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> productsService.getProductById(id));

        assertThat(exception).hasMessage("Product with id 123 doesn't exist");
    }
}


