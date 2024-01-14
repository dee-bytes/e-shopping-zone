package com.eshop.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eshop.product.Entity.Product;


public interface ProductService {
	
	
	void addProducts(Product product);
	List<Product> getAllProducts();
	Product getProductById(String productId);
	Product getProductByName(String productName);
	Product updateProducts(Product product);
	void deleteProductById(String productId);
	List<Product> getProductByCategory(String category);
	List<Product> getProductByType(String productType);
	
}
