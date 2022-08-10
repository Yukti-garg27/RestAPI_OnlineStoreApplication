package com.springboot.onlineStore.services;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import com.springboot.onlineStore.model.Order;
import com.springboot.onlineStore.model.Product;
import com.springboot.onlineStore.repositories.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {
    private static final Product PRODUCT = new Product("Apple Laptop",
            null,
            null,
            5.5f,
            "electronics");
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smith";
    private static final String EMAIL = "abc@company.com";
    private static final String ADDRESS = "130 Green Olive Dr. San Francisco";
    private static final String CREDIT_CARD = "1234123412341234";

    private static final int MAX_NUMBER_OF_ITEMS = 5;

    @Mock
    private OrderRepository orderRepository;
    private OrdersService ordersService;

    @BeforeEach
    public void setupTest() {
        ordersService = new OrdersService(orderRepository, MAX_NUMBER_OF_ITEMS);
    }

    @Test
    public void placeOrders_success_returnsNothing() {
        Order order = new Order(FIRST_NAME,
                LAST_NAME,
                EMAIL,
                ADDRESS,
                2,
                PRODUCT
                , CREDIT_CARD);
        List<Order> orders = Arrays.asList(order);

        ordersService.placeOrders(orders);

        verify(orderRepository).saveAll(orders);
    }

    @Test
    public void placeOrders_numberOfItemsExceeded_throwsException() {
        Order order = new Order(FIRST_NAME,
                LAST_NAME,
                EMAIL,
                ADDRESS,
                20,
                PRODUCT
                , CREDIT_CARD);

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    ordersService.placeOrders(Arrays.asList(order));
                });

    }
}
