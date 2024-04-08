package com.eshop.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eshop.product.Entity.Product;
import com.eshop.product.Exceptions.ProductNotFoundException;
import com.eshop.product.Repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    /**
     * Method under test: {@link ProductServiceImpl#addProducts(Product)}
     */
    @Test
    void testAddProducts() {
        // Arrange
        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating("Rating");
        product.setReview("Review");
        product.setSpecification("Specification");
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product);

        Product product2 = new Product();
        product2.setCategory("Category");
        product2.setDescription("The characteristics of someone or something");
        product2.setImage("Image");
        product2.setPrice(10.0d);
        product2.setProductId("42");
        product2.setProductName("Product Name");
        product2.setProductType("Product Type");
        product2.setRating("Rating");
        product2.setReview("Review");
        product2.setSpecification("Specification");

        // Act
        productServiceImpl.addProducts(product2);

        // Assert that nothing has changed
        verify(productRepository).save(isA(Product.class));
        assertEquals("42", product2.getProductId());
        assertEquals("Category", product2.getCategory());
        assertEquals("Image", product2.getImage());
        assertEquals("Product Name", product2.getProductName());
        assertEquals("Product Type", product2.getProductType());
        assertEquals("Rating", product2.getRating());
        assertEquals("Review", product2.getReview());
        assertEquals("Specification", product2.getSpecification());
        assertEquals("The characteristics of someone or something", product2.getDescription());
        assertEquals(10.0d, product2.getPrice());
        assertTrue(productServiceImpl.getAllProducts().isEmpty());
    }

    /**
     * Method under test: {@link ProductServiceImpl#addProducts(Product)}
     */
    @Test
    void testAddProducts2() {
        // Arrange
        when(productRepository.save(Mockito.<Product>any())).thenThrow(new ProductNotFoundException("Msg"));

        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating("Rating");
        product.setReview("Review");
        product.setSpecification("Specification");

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productServiceImpl.addProducts(product));
        verify(productRepository).save(isA(Product.class));
    }

    /**
     * Method under test: {@link ProductServiceImpl#getAllProducts()}
     */
    @Test
    void testGetAllProducts() {
        // Arrange
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);

        // Act
        List<Product> actualAllProducts = productServiceImpl.getAllProducts();

        // Assert
        verify(productRepository).findAll();
        assertTrue(actualAllProducts.isEmpty());
        assertSame(productList, actualAllProducts);
    }

    /**
     * Method under test: {@link ProductServiceImpl#getAllProducts()}
     */
    @Test
    void testGetAllProducts2() {
        // Arrange
        when(productRepository.findAll()).thenThrow(new ProductNotFoundException("Msg"));

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productServiceImpl.getAllProducts());
        verify(productRepository).findAll();
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductById(String)}
     */
    @Test
    void testGetProductById() {
        // Arrange
        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating("Rating");
        product.setReview("Review");
        product.setSpecification("Specification");
        Optional<Product> ofResult = Optional.of(product);
        when(productRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Product actualProductById = productServiceImpl.getProductById("42");

        // Assert
        verify(productRepository).findById(eq("42"));
        assertSame(product, actualProductById);
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductById(String)}
     */
    @Test
    void testGetProductById2() {
        // Arrange
        Optional<Product> emptyResult = Optional.empty();
        when(productRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productServiceImpl.getProductById("42"));
        verify(productRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductById(String)}
     */
    @Test
    void testGetProductById3() {
        // Arrange
        when(productRepository.findById(Mockito.<String>any())).thenThrow(new ProductNotFoundException("Msg"));

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productServiceImpl.getProductById("42"));
        verify(productRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductByName(String)}
     */
    @Test
    void testGetProductByName() {
        // Arrange
        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating("Rating");
        product.setReview("Review");
        product.setSpecification("Specification");
        when(productRepository.findByProductName(Mockito.<String>any())).thenReturn(product);

        // Act
        Product actualProductByName = productServiceImpl.getProductByName("Product Name");

        // Assert
        verify(productRepository).findByProductName(eq("Product Name"));
        assertSame(product, actualProductByName);
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductByName(String)}
     */
    @Test
    void testGetProductByName2() {
        // Arrange
        when(productRepository.findByProductName(Mockito.<String>any())).thenThrow(new ProductNotFoundException("Msg"));

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productServiceImpl.getProductByName("Product Name"));
        verify(productRepository).findByProductName(eq("Product Name"));
    }

    /**
     * Method under test: {@link ProductServiceImpl#updateProducts(Product)}
     */
    @Test
    void testUpdateProducts() {
        // Arrange
        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating("Rating");
        product.setReview("Review");
        product.setSpecification("Specification");
        Optional<Product> ofResult = Optional.of(product);

        Product product2 = new Product();
        product2.setCategory("Category");
        product2.setDescription("The characteristics of someone or something");
        product2.setImage("Image");
        product2.setPrice(10.0d);
        product2.setProductId("42");
        product2.setProductName("Product Name");
        product2.setProductType("Product Type");
        product2.setRating("Rating");
        product2.setReview("Review");
        product2.setSpecification("Specification");
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product2);
        when(productRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        Product product3 = new Product();
        product3.setCategory("Category");
        product3.setDescription("The characteristics of someone or something");
        product3.setImage("Image");
        product3.setPrice(10.0d);
        product3.setProductId("42");
        product3.setProductName("Product Name");
        product3.setProductType("Product Type");
        product3.setRating("Rating");
        product3.setReview("Review");
        product3.setSpecification("Specification");

        // Act
        Product actualUpdateProductsResult = productServiceImpl.updateProducts(product3);

        // Assert
        verify(productRepository).findById(eq("42"));
        verify(productRepository).save(isA(Product.class));
        assertSame(product2, actualUpdateProductsResult);
    }

    /**
     * Method under test: {@link ProductServiceImpl#updateProducts(Product)}
     */
    @Test
    void testUpdateProducts2() {
        // Arrange
        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating("Rating");
        product.setReview("Review");
        product.setSpecification("Specification");
        Optional<Product> ofResult = Optional.of(product);
        when(productRepository.save(Mockito.<Product>any())).thenThrow(new ProductNotFoundException("Msg"));
        when(productRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        Product product2 = new Product();
        product2.setCategory("Category");
        product2.setDescription("The characteristics of someone or something");
        product2.setImage("Image");
        product2.setPrice(10.0d);
        product2.setProductId("42");
        product2.setProductName("Product Name");
        product2.setProductType("Product Type");
        product2.setRating("Rating");
        product2.setReview("Review");
        product2.setSpecification("Specification");

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productServiceImpl.updateProducts(product2));
        verify(productRepository).findById(eq("42"));
        verify(productRepository).save(isA(Product.class));
    }

    /**
     * Method under test: {@link ProductServiceImpl#updateProducts(Product)}
     */
    @Test
    void testUpdateProducts3() {
        // Arrange
        Optional<Product> emptyResult = Optional.empty();
        when(productRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating("Rating");
        product.setReview("Review");
        product.setSpecification("Specification");

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productServiceImpl.updateProducts(product));
        verify(productRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link ProductServiceImpl#deleteProductById(String)}
     */
    @Test
    void testDeleteProductById() {
        // Arrange
        doNothing().when(productRepository).deleteById(Mockito.<String>any());

        // Act
        productServiceImpl.deleteProductById("42");

        // Assert that nothing has changed
        verify(productRepository).deleteById(eq("42"));
        assertTrue(productServiceImpl.getAllProducts().isEmpty());
    }

    /**
     * Method under test: {@link ProductServiceImpl#deleteProductById(String)}
     */
    @Test
    void testDeleteProductById2() {
        // Arrange
        doThrow(new ProductNotFoundException("Msg")).when(productRepository).deleteById(Mockito.<String>any());

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productServiceImpl.deleteProductById("42"));
        verify(productRepository).deleteById(eq("42"));
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductByCategory(String)}
     */
    @Test
    void testGetProductByCategory() {
        // Arrange
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findByCategory(Mockito.<String>any())).thenReturn(productList);

        // Act
        List<Product> actualProductByCategory = productServiceImpl.getProductByCategory("Category");

        // Assert
        verify(productRepository).findByCategory(eq("Category"));
        assertTrue(actualProductByCategory.isEmpty());
        assertSame(productList, actualProductByCategory);
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductByCategory(String)}
     */
    @Test
    void testGetProductByCategory2() {
        // Arrange
        when(productRepository.findByCategory(Mockito.<String>any())).thenThrow(new ProductNotFoundException("Msg"));

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productServiceImpl.getProductByCategory("Category"));
        verify(productRepository).findByCategory(eq("Category"));
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductByType(String)}
     */
    @Test
    void testGetProductByType() {
        // Arrange
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findByProductType(Mockito.<String>any())).thenReturn(productList);

        // Act
        List<Product> actualProductByType = productServiceImpl.getProductByType("Product Type");

        // Assert
        verify(productRepository).findByProductType(eq("Product Type"));
        assertTrue(actualProductByType.isEmpty());
        assertSame(productList, actualProductByType);
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductByType(String)}
     */
    @Test
    void testGetProductByType2() {
        // Arrange
        when(productRepository.findByProductType(Mockito.<String>any())).thenThrow(new ProductNotFoundException("Msg"));

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productServiceImpl.getProductByType("Product Type"));
        verify(productRepository).findByProductType(eq("Product Type"));
    }
}
