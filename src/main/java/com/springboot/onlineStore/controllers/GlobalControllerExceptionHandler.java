package com.springboot.onlineStore.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler
 * Printing out to the screen(for now)
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(value=Exception.class)
	public ResponseEntity<String> defaultErrorHandler(HttpServletRequest req,
			Exception ex) throws Exception{
		System.out.println("Exception in handling request to "+req.getRequestURI()
		+" : "+ex.getMessage());
		
		ex.printStackTrace();
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
   
}
