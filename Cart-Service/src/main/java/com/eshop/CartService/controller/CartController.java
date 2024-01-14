package com.eshop.CartService.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eshop.CartService.entity.Cart;
import com.eshop.CartService.exception.CartNotFoundException;
import com.eshop.CartService.service.CartService;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	RestTemplate restTemplate;

	Logger logger = LoggerFactory.getLogger(CartController.class);

	@GetMapping("/all")
	public ResponseEntity<List<Cart>> getAllCarts() {
		return ResponseEntity.ok(cartService.getAllCarts());
	}

	@PostMapping("/addingproducttocart/{cartId}/{productId}")
	public ResponseEntity<Cart> addProductToCart(@PathVariable String cartId, @PathVariable String productId) {
		Cart updatedCart = cartService.addProductToCart(cartId, productId);
		return new ResponseEntity<>(updatedCart, HttpStatus.CREATED);
	}

	@GetMapping("/{cartId}")
	public ResponseEntity<Cart> getCartById(@PathVariable String cartId) {
		return new ResponseEntity<>(cartService.getCartById(cartId), HttpStatus.CREATED);
	}

	@PutMapping("/delete/item/{cartId}/{productId}")
	public ResponseEntity<Cart> deleteCartItem(@PathVariable String cartId, @PathVariable String productId) {
		// Call the service to delete the item
		Cart updatedCart = cartService.deleteCartItem(cartId, productId);
		return new ResponseEntity<>(updatedCart, HttpStatus.OK);
	}

	@PutMapping("/decreaseQuant/{productId}/{cartId}")
	public Cart decreaseItem(@PathVariable String productId, @PathVariable String cartId) {
		try {
			return cartService.decreaseItem(productId, cartId);
		} catch (CartNotFoundException e) {
			// Handle CartNotFoundException appropriately, e.g., return a specific error
			// response
			// For simplicity, rethrowing it here
			throw new RuntimeException("Cart not found with ID: " + cartId, e);
		}
	}

	@DeleteMapping("/delete/{cartId}")
	public void deleteCart(@PathVariable String cartId) {
		cartService.deleteCartById(cartId);
	}

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	public CartController() {

	}

//	@PostMapping("/addingproducttocart/{cartId}/{productId}")
//	public ResponseEntity<Cart> addProductToCart(@PathVariable int cartId, @PathVariable int productId) {
//		Product product = restTemplate.getForObject("http://localhost:3334/getproduct/" + productId,
//				Product.class);
//		logger.info("" + product);
//		logger.info("Checking cartId is exists or not");
//		// if cart exists -->
//		if (cartRepository.existsById(cartId)) {
//			Cart oldCart = cartRepository.findById(cartId);
//			List<Integer> idList = new ArrayList<>();
//			List<Items> oldItem3 = oldCart.getItems();
//			for (Items i : oldItem3) {
//				idList.add(i.getProductId());
//			}
//			logger.info("Checking same product is already in the existing Cart");
//			if (idList.contains(product.getProductId())) {
//				logger.info("in if method");
//				List<Items> oldItem2 = oldCart.getItems();
//				logger.info("Checking given productId matching");
//				for (Items i : oldItem2) {
//					if (i.getProductId() == productId) {
//						logger.info("If ProductId matches then Increasing the quantity");
//						i.setQuantity(i.getQuantity() + 1);
//					}
//				}
//				double price = 0;
//				for (Items i : oldItem2) {
//					logger.info("Calculating the price according to Quantity");
//					price = price + i.getPrice() * i.getQuantity();
//				}
//				oldCart.setTotalPrice(price);
//				return new ResponseEntity<>(cartServiceImpl.addCart(oldCart), HttpStatus.CREATED);
//			} else {
//				logger.info("If the Product Not already exists,adding new Product to Cart");
//				Items items = new Items();
//				items.setProductId(productId);
//				items.setPrice(product.getPrice());
//				items.setProductName(product.getProductName());
//				items.setQuantity(1);
//				items.setImage(product.getImage());
//				List<Items> oldItems = oldCart.getItems();
//				oldItems.add(items);
//				oldCart.setItems(oldItems);
//				double price = 0;
//				logger.info("Calculating price");
//				for (Items i : oldItems) {
//					price = price + i.getPrice() * i.getQuantity();
//				}
//				oldCart.setTotalPrice(price);
//				return new ResponseEntity<>(cartServiceImpl.addCart(oldCart), HttpStatus.CREATED);
//			}
//		} else {
//			logger.info("If the Cart is not there,creating new cart");
//			Cart cart = new Cart();
//			cart.setCartId(cartId);
//			Items items = new Items();
//			logger.info("Adding Product details");
//			items.setProductId(productId);
//			items.setPrice(product.getPrice());
//			items.setProductName(product.getProductName());
//			items.setQuantity(1);
//			items.setImage(product.getImage());
//			List<Items> list = new ArrayList<>();
//			list.add(items);
//			cart.setItems(list);
//			double price = 0;
//			logger .info("Calculating price according quantity");
//			for (Items i : list) {
//				price = price + i.getPrice() * i.getQuantity();
//			}
//			cart.setTotalPrice(price);
//			return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.CREATED);
//
//		}
//	}

//	@PutMapping("/delete/item/{productId}/{cartId}")
//	public Cart deleteCartItem(@PathVariable int cartId, @PathVariable int productId) {
//		
//		Product product = restTemplate.getForObject("http://localhost:3334/getproduct/" + productId,Product.class);
//		Cart cart2 = cartServiceImpl.getCartById(cartId);
//		List<Items> list = new ArrayList<>();
//		list = cart2.getItems();
//		System.out.println(list);
//		logger.info("Deleting particular product from cart");
//		list.removeIf(i -> (i.getProductId() == productId));
//		cart2.setItems(list);
//		double price = 0;
//		logger.info("After removing product from cart again calculating total price");
//		for (Items i : list) {
//			price = price + i.getPrice() * i.getQuantity();
//
//		}
//		cart2.setTotalPrice(price);
//		return cartServiceImpl.updateCart(cart2.getCartId(), cart2);
//	}

//	@PutMapping("/decreaseQuant/{productId}/{cartId}")
//	public Cart decreaseItem(@PathVariable int productId, @PathVariable int cartId) {
//		Cart cart = cartServiceImpl.getCartById(cartId);
//		List<Items> oldItem3 = cart.getItems();
//		logger.info("Checking given product matches to existing product");
//		for (Items i : oldItem3) {
//			if (i.getProductId() == productId) {
//				logger.info("If the Product matches,then decreasing quantity by 1");
//				i.setQuantity(i.getQuantity() - 1);
//			}
//		}
//		double price = 0;
//		logger.info("Setting the price");
//		for (Items i : oldItem3) {
//			price = price + i.getPrice() * i.getQuantity();
//		}
//		cart.setTotalPrice(price);
//		return cartServiceImpl.updateCart(cartId, cart);
//
//	}
	
	

}
