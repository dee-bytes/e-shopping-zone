package com.eshop.product.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eshop.product.Entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

		
	Product findByProductName(String productName);
	List<Product> findByCategory(String category);
	List<Product> findByProductType(String productType);
}
