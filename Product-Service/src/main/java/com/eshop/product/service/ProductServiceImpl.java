package com.eshop.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.product.Entity.Product;
import com.eshop.product.Exceptions.ProductCategoryNotFoundException;
import com.eshop.product.Exceptions.ProductNotFoundException;
import com.eshop.product.Exceptions.ProductTypeNotExistsException;
import com.eshop.product.Repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepo;

	@Override
	public void addProducts(Product product) {
		productRepo.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public Product getProductById(String productId) {
		return productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
	}

	@Override
	public Product getProductByName(String productName) {
		Product product = productRepo.findByProductName(productName);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with name: " + productName);
        }
        return product;
	}

	@Override
	public Product updateProducts(Product product) {
		Product existingProduct = productRepo.findById(product.getProductId())
		        .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + product.getProductId()));

		
		existingProduct.setProductName(product.getProductName());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setRating(product.getRating());
		existingProduct.setCategory(product.getCategory());
		existingProduct.setDescription(product.getDescription());
		existingProduct.setImage(product.getImage());
		existingProduct.setProductType(product.getProductType());
		existingProduct.setReview(product.getReview());
		existingProduct.setSpecification(product.getSpecification());

		    return productRepo.save(existingProduct);
	}

	@Override
	public void deleteProductById(String productId) {
		productRepo.deleteById(productId);
		
	}

	@Override
	public List<Product> getProductByCategory(String category) {
		return productRepo.findByCategory(category);
	}

	@Override
	public List<Product> getProductByType(String productType) {
		return productRepo.findByProductType(productType);
    }
	
	
	
	
	
	
	
	
	
	

	
}
