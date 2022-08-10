package com.springboot.onlineStore.exceptions;

import java.util.HashSet;
import java.util.Set;

/**
 * Credit Card Validation exception, thrown when an invalid or stolen credit card is used during purchase
 */
public class CreditCardValidationException extends RuntimeException {

	public CreditCardValidationException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	
    
}
