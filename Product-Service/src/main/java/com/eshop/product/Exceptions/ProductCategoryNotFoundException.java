package com.eshop.product.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ProductCategoryNotFoundException extends RuntimeException{
	

	public ProductCategoryNotFoundException(String msg){
		super(msg);
	}

}
