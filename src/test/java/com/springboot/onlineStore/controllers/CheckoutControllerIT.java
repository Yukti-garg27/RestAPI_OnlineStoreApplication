package com.springboot.onlineStore.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.springboot.onlineStore.exceptions.CreditCardValidationException;
import com.springboot.onlineStore.model.Order;
import com.springboot.onlineStore.model.Product;
import com.springboot.onlineStore.repositories.ProductCategoryRepository;
import com.springboot.onlineStore.repositories.ProductRepository;
import com.springboot.onlineStore.services.CreditCardValidationService;
import com.springboot.onlineStore.services.OrdersService;
import com.springboot.onlineStore.services.ProductsService;

/**
 * CheckoutController Integration Tests
 */
@WebMvcTest(CheckoutController.class)
public class CheckoutControllerIT {
	private static final String VALID_CHECKOUT_REQUEST = "{\n" + "  \"firstName\": \"John\",\n"
			+ "  \"lastName\": \"Smith\",\n" + "  \"email\": \"john.smith@company.com\",\n"
			+ "  \"shippingAddress\": \"130 Green Olive Dr. San Francisco\",\n"
			+ "  \"products\": [{\"productId\":123, \"quantity\" : 5}],\n" + "  \"creditCard\" : \"1234123412341234\"\n"
			+ "}";
	private static final String CHECKOUT_REQUEST_WITH_STOLEN_CREDIT_CARD = "{\n" + "  \"firstName\": \"John\",\n"
			+ "  \"lastName\": \"Smith\",\n" + "  \"email\": \"john.smith@company.com\",\n"
			+ "  \"shippingAddress\": \"130 Green Olive Dr. San Francisco\",\n"
			+ "  \"products\": [{\"productId\":123, \"quantity\" : 5}],\n" + "  \"creditCard\" : \"1111111111111111\"\n"
			+ "}";
	private static final Product PRODUCT = new Product("Audio Speakers",
			"Stereo speakers for listening to music at home", "speakers.jpeg", 89f, "electronics");

	@MockBean
	private OrdersService ordersService;
	@MockBean
	private ProductsService productsService;
	@MockBean
	private CreditCardValidationService creditCardValidationService;
	@MockBean
	private ProductCategoryRepository productCategoryRepository;
	@MockBean
	private ProductRepository productRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void checkout_withCorrectRequest_success() throws Exception {
		when(productsService.getProductById(123)).thenReturn(PRODUCT);
		Iterable<Order> expectedOrders = Sets.set(new Order("John", "Smith",
				"john.smith@company.com",
				"130 Green Olive Dr. San Francisco", 5L, PRODUCT, "1234123412341234"));

		// Act and Assert
		mockMvc.perform(post("/checkout").content(VALID_CHECKOUT_REQUEST)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(content().string("Order placed successfully!!"))
				.andExpect(status().isOk());

	//	Mockito.verify(ordersService).placeOrders(expectedOrders);
	}

	
	  @Test
	  public void checkout_invalidCreditCard_returnsBadRequestStatus() throws Exception {
		  doThrow(new CreditCardValidationException("Invalid Credit Card"))
	  .when(creditCardValidationService) .validate(eq("1111111111111111"));
	 
	  // Act and Assert
	    mockMvc.perform(post("/checkout")
	  .content(CHECKOUT_REQUEST_WITH_STOLEN_CREDIT_CARD)
	  .contentType(MediaType.APPLICATION_JSON_VALUE)) 
	    .andDo(print())
	  .andExpect(content().string("Credit card is invalid, please use another form of " +
	  "payment")) .andExpect(status().isBadRequest()); }
	 
}
