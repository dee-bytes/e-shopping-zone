package com.eshop.CartService.exception;


public class CartNotFoundException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	
		private String message;
		
		public  CartNotFoundException(String message) {
			
			super(message);
			this.message=message;
		}
		
		public CartNotFoundException() {
			
		}
	
}
