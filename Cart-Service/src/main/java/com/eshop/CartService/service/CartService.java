package com.eshop.CartService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eshop.CartService.entity.Cart;
import com.eshop.CartService.exception.CartNotFoundException;


public interface CartService {
	Cart getCartById(String cartId);
	Cart updateCart(String cartId, Cart cart);
	List<Cart> getAllCarts();
	double cartTotal(Cart cart);
	Cart addCart(Cart cart);
	String deleteCartById(String cartId);
	Cart addProductToCart(String cartId,String productId);
	Cart deleteCartItem(String cartId, String productId) throws CartNotFoundException;
	Cart decreaseItem(String productId, String cartId) throws CartNotFoundException;

}
