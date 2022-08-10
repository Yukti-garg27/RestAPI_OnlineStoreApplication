package com.springboot.onlineStore.services;

import com.springboot.onlineStore.exceptions.CreditCardValidationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreditCardValidationService {
	private static final String CREDIT_CARD_FORMAT="^[0-9]{16}$";
	private static final Set<String> STOLEN_CREDIT_CARDS=new HashSet<>();
	
	public CreditCardValidationService() {
		STOLEN_CREDIT_CARDS.add("1111111111111111");
		STOLEN_CREDIT_CARDS.add("99998888877776666");
	}
	public void validate(String creditCard) {
		// TODO Auto-generated method stub
		validateNumberOfDigits(creditCard);
		validateNotStolenCreditCard(creditCard);
		
	}
	
	private void validateNumberOfDigits(String creditCard) {
		if(!creditCard.matches(CREDIT_CARD_FORMAT))
			throw new CreditCardValidationException(
					String.format("%s is invalid credit card",creditCard));
		
	}
	private void validateNotStolenCreditCard(String creditCard) {
		if(STOLEN_CREDIT_CARDS.contains(creditCard))
			throw new CreditCardValidationException(
					String.format("%s is stolen credit card",creditCard));
		
	}
}
