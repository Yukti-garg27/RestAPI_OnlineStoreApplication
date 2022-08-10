package com.springboot.onlineStore.services;

import com.springboot.onlineStore.model.Order;
import com.springboot.onlineStore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Integrates with the database API and handles user's orders
 */
@Service
public class OrdersService {
	
	private OrderRepository orderRepository;
	
	private final Long maxNumberOfItems;
	
	public OrdersService(OrderRepository orderRepository, 
			@Value("${products.service.max-number-of-items}") long maxNumberOfItems) {
		this.orderRepository=orderRepository;
		this.maxNumberOfItems=maxNumberOfItems;
	}
   
	public void placeOrders(Iterable<Order> orders) {
		validateNumberOfItemsOrderd(orders);
		orderRepository.saveAll(orders);
	}
	
	private void validateNumberOfItemsOrderd(Iterable<Order> orders) {
		long totalNumberOfItems=0;
		for(Order order:orders) {
			totalNumberOfItems+=order.getQuantity();
		}
		if(totalNumberOfItems>maxNumberOfItems)
			throw new IllegalStateException(String.format("Number of products %d "
					+ "exceeded the limit of %d ",totalNumberOfItems,maxNumberOfItems));
	}
	
}
