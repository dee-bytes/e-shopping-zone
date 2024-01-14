package com.eshop.payment.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eshop.payment.entity.Cart;


@FeignClient(value="cart-service",url="http://localhost:3333/cart")
public interface PaymentInterface {
	@GetMapping("/{cartId}")
	public ResponseEntity<Cart> getCartById(@PathVariable String cartId) ;
	
	
	@DeleteMapping("/delete/{cartId}")
	public void deleteCart(@PathVariable String cartId);
	

}
