package com.eshop.CartService.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eshop.CartService.entity.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
	Cart findByCartId(String cartId);
}
