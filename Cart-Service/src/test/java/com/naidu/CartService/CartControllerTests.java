package com.naidu.CartService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eshop.CartService.controller.CartController;
import com.eshop.CartService.entity.Cart;
import com.eshop.CartService.repository.CartRepository;
import com.eshop.CartService.service.CartService;
import com.eshop.CartService.service.CartServiceImpl;

class CartControllerTests {

	@Mock
	private CartService cartService;

	@Mock
	private CartRepository cartRepository;

	@Mock
	private CartServiceImpl cartServiceImpl;

	@InjectMocks
	private CartController cartController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void decreaseItem_ShouldReturnCart() {
		// Arrange
		String productId = "1";
		String cartId = "1";
		Cart cart = new Cart();
		when(cartService.decreaseItem(productId, cartId)).thenReturn(cart);

		// Act
		Cart result = cartController.decreaseItem(productId, cartId);

		// Assert
		assertNotNull(result);
		// Add more assertions if needed
	}

}
