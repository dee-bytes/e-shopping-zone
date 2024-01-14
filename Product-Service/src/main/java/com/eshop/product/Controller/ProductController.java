package com.eshop.product.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.product.Entity.Product;
import com.eshop.product.Exceptions.ProductNotFoundException;
import com.eshop.product.service.ProductService;
import com.eshop.product.service.ProductServiceImpl;

import jakarta.validation.Valid;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/product")
@CrossOrigin("http://localhost:3000")
public class ProductController {
	private final Logger logger = LogManager.getLogger();
	@Autowired
	private ProductService productService;

	@PostMapping("/add")
	public ResponseEntity<Void> addProduct(@RequestBody Product product) {
		logger.info("Sending request to the product");
		productService.addProducts(product);
		logger.info("added");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProduct() {
		logger.info("Sending request to the product");
		List<Product> products = productService.getAllProducts();
		logger.info("get all products");
		return new ResponseEntity<>(products, HttpStatus.CREATED);

	}

	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable String productId) throws ProductNotFoundException {

		logger.info("Sending request to the product");
		Product p1 = productService.getProductById(productId);
		logger.info("get product by id");
		return new ResponseEntity<>(p1, HttpStatus.OK);

	}

	@GetMapping("/{productName}")
	public ResponseEntity<Product> getProductByName(@PathVariable String productName) {
		logger.info("Sending request to the product");
		Product p1 = productService.getProductByName(productName);
		logger.info("get product by name");
		return new ResponseEntity<>(p1, HttpStatus.CREATED);

	}

	@PutMapping("/update/{productId}")
	public ResponseEntity<Product> updateProducts(@PathVariable String productId, @RequestBody Product product) {
		logger.info("Sending request to the product");
		Product p1 = productService.updateProducts(product);
		logger.info("get product by name");
		return new ResponseEntity<>(p1, HttpStatus.CREATED);

	}

	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<Void> deleteProductById(@PathVariable String productId) {
		logger.info("Sending request to the product");
		productService.deleteProductById(productId);
		logger.info("get product by name");
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping("/category/{category}")
	public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String category) {
		logger.info("Sending request to the product");
		List<Product> p1 = productService.getProductByCategory(category);
		logger.info("get product by name");
		return new ResponseEntity<>(p1, HttpStatus.CREATED);

	}

	@GetMapping("/type/{productType}")
	public ResponseEntity<List<Product>> getProductByType(@PathVariable String productType) {
		logger.info("Sending request to the product");
		List<Product> p1 = productService.getProductByType(productType);
		logger.info("get product by name");
		return new ResponseEntity<>(p1, HttpStatus.CREATED);
	}

//	@GetMapping("/getAllProductbyName/{productName}")
//	public ResponseEntity<List<Product>> getAllProductByName(@PathVariable String productName){
//		logger.info("Sending request to the product");
//		List<Product> p1=productService.getAllProductsByName(productName);
//		logger.info("get product by name");
//		return new ResponseEntity<>(p1,HttpStatus.CREATED);
//	}

}
