package com.OnlineShoppingCart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.eshop.product.Entity.Product;
import com.eshop.product.Repository.ProductRepository;
import com.eshop.product.service.ProductService;
import com.eshop.product.service.ProductServiceImpl;

@SpringBootTest
 class ProductServiceImplTest {
	@Autowired
	private ProductService productService;
	@MockBean
	private ProductRepository productrepo;

//	@Test
//	public void getallproduct() {
//		List<Product> products = new ArrayList<>();
//		Product p = new Product();
//
//		p.setProductId("3");
//		p.setProductName("iphone");
//		p.setProductType("samsung");
//		p.setCategory("oneplus");
//		p.setDescription("gudd");
//		p.setPrice(100);
//		p.setRating("5");
//		p.setImage("circle");
//		p.setReview("good");
//		p.setSpecification("imax");
//
//		products.add(p);
//
//		when(productrepo.findAll()).thenReturn(products);
//		assertEquals(1, productService.getAllProduct().size());
//	}
//	
//	@Test
//	public void ProductById() {
//		//List<Product> products = new ArrayList<>();
//
//		Product p = new Product();
//
//		p.setProductId(3);
//		p.setProductName("iphone");
//		p.setProductType("samsung");
//		p.setCategory("oneplus");
//		p.setDescription("gudd");
//		p.setPrice(100);
//		p.setRating("5");
//		p.setImage("circle");
//		p.setReview("good");
//		p.setSpecification("imax");
//		//products.add(p);
//
//
//		Optional<Product> byproductid = Optional.of(p);
//		when(productrepo.findById(3)).thenReturn(byproductid);
//		assertEquals(p, productSerImpl.getProductById(3));
//
//	}
//
//
////	@Test
////	public void addProduct_test() {
////
////		Product p = new Product();
////		p.setProductId(3);
////		p.setProductName("iphone");
////		p.setProductType("samsung");
////		p.setCategory("oneplus");
////		p.setDescription("gudd");
////		p.setPrice(100);
////		p.setRating("5");
////		p.setImage("circle");
////		p.setReview("good");
////		p.setSpecification("imax");
////
////		
////
////		when(productrepo.insert(p)).thenReturn(p);
////		assertEquals(p, productSerImpl.addProduct(p));
////
////	}
//	
//	
//	
//	
//	
//	
//	
//	@Test
//	public void getProductByName_test() {
//		
//		Product p = new Product();
//
//		p.setProductId(3);
//		p.setProductName("iphone");
//		p.setProductType("samsung");
//		p.setCategory("oneplus");
//		p.setDescription("gudd");
//		p.setPrice(100);
//		p.setRating("5");
//		p.setImage("circle");
//		p.setReview("good");
//		p.setSpecification("imax");
//
//		List<Product> byproductname = List.of(p);
//		when(productrepo.findByProductName("iphone")).thenReturn(byproductname);
//		assertEquals(byproductname, productSerImpl.getProductByName("iphone"));
//		
//	}
//	
//	
//	
//	
//	
//
//	@Test
//	public void getProductByCategory() {
//		Product p = new Product();
//
//		p.setProductId(3);
//		p.setProductName("iphone");
//		p.setProductType("samsung");
//		p.setCategory("oneplus");
//		p.setDescription("gudd");
//		p.setPrice(100);
//		p.setRating("5");
//		p.setImage("circle");
//		p.setReview("good");
//		p.setSpecification("imax");
//
//		List<Product> byproductcategory = List.of(p);
//		when(productrepo.findByCategory("oneplus")).thenReturn(byproductcategory);
//		assertEquals(byproductcategory, productSerImpl.getProductByCategory("oneplus"));
//		
//	}
//	
//	
//	@Test
//	public void getProductByType_test() {
//		
//		Product p = new Product();
//		p.setProductId(3);
//		p.setProductName("iphone");
//		p.setProductType("samsung");
//		p.setCategory("oneplus");
//		p.setDescription("gudd");
//		p.setPrice(100);
//		p.setRating("5");
//		p.setImage("circle");
//		p.setReview("good");
//		p.setSpecification("imax");
//		
//		List<Product> byproducttype = List.of(p);
//		when(productrepo.findByProductType("samsung")).thenReturn(byproducttype);
//		assertEquals(byproducttype, productSerImpl.getProductByType("samsung"));
//	}
	
}

