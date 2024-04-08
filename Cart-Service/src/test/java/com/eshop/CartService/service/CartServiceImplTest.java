package com.eshop.CartService.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eshop.CartService.entity.Cart;
import com.eshop.CartService.entity.Items;
import com.eshop.CartService.entity.Product;
import com.eshop.CartService.exception.CartAlreadyExistsException;
import com.eshop.CartService.exception.CartNotFoundException;
import com.eshop.CartService.repository.CartRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {CartServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CartServiceImplTest {
    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private CartServiceImpl cartServiceImpl;

    @MockBean
    private RestTemplate restTemplate;

    /**
     * Method under test: {@link CartServiceImpl#getCartById(String)}
     */
    @Test
    void testGetCartById() throws CartNotFoundException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        // Act
        Cart actualCartById = cartServiceImpl.getCartById("42");

        // Assert
        verify(cartRepository).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        assertSame(cart, actualCartById);
    }

    /**
     * Method under test: {@link CartServiceImpl#getCartById(String)}
     */
    @Test
    void testGetCartById2() throws CartNotFoundException {
        // Arrange
        when(cartRepository.findByCartId(Mockito.<String>any())).thenThrow(new CartNotFoundException("An error occurred"));
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.getCartById("42"));
        verify(cartRepository).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
    }

    /**
     * Method under test: {@link CartServiceImpl#getCartById(String)}
     */
    @Test
    void testGetCartById3() throws CartNotFoundException {
        // Arrange
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(false);

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.getCartById("42"));
        verify(cartRepository).existsById(eq("42"));
    }

    /**
     * Method under test: {@link CartServiceImpl#updateCart(String, Cart)}
     */
    @Test
    void testUpdateCart() throws CartNotFoundException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        ArrayList<Items> items = new ArrayList<>();
        cart2.setItems(items);
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);

        Cart cart3 = new Cart();
        cart3.setCartId("42");
        cart3.setItems(new ArrayList<>());
        cart3.setTotalPrice(10.0d);

        // Act
        Cart actualUpdateCartResult = cartServiceImpl.updateCart("42", cart3);

        // Assert
        verify(cartRepository).findByCartId(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        assertEquals("42", actualUpdateCartResult.getCartId());
        assertEquals(10.0d, actualUpdateCartResult.getTotalPrice());
        assertEquals(items, actualUpdateCartResult.getItems());
        assertSame(cart, actualUpdateCartResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#updateCart(String, Cart)}
     */
    @Test
    void testUpdateCart2() throws CartNotFoundException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenThrow(new CartNotFoundException("An error occurred"));
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setTotalPrice(10.0d);

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.updateCart("42", cart2));
        verify(cartRepository).findByCartId(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
    }

    /**
     * Method under test: {@link CartServiceImpl#getAllCarts()}
     */
    @Test
    void testGetAllCarts() throws CartNotFoundException {
        // Arrange
        when(cartRepository.findAll()).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.getAllCarts());
        verify(cartRepository).findAll();
    }

    /**
     * Method under test: {@link CartServiceImpl#getAllCarts()}
     */
    @Test
    void testGetAllCarts2() throws CartNotFoundException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);

        ArrayList<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        when(cartRepository.findAll()).thenReturn(cartList);

        // Act
        List<Cart> actualAllCarts = cartServiceImpl.getAllCarts();

        // Assert
        verify(cartRepository).findAll();
        assertEquals(1, actualAllCarts.size());
        assertSame(cart, actualAllCarts.get(0));
    }

    /**
     * Method under test: {@link CartServiceImpl#getAllCarts()}
     */
    @Test
    void testGetAllCarts3() throws CartNotFoundException {
        // Arrange
        when(cartRepository.findAll()).thenThrow(new CartNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.getAllCarts());
        verify(cartRepository).findAll();
    }

    /**
     * Method under test: {@link CartServiceImpl#cartTotal(Cart)}
     */
    @Test
    void testCartTotal() {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);

        // Act and Assert
        assertEquals(10.0d, cartServiceImpl.cartTotal(cart));
    }

    /**
     * Method under test: {@link CartServiceImpl#cartTotal(Cart)}
     */
    @Test
    void testCartTotal2() {
        // Arrange
        Cart cart = mock(Cart.class);
        when(cart.getTotalPrice()).thenReturn(10.0d);
        doNothing().when(cart).setCartId(Mockito.<String>any());
        doNothing().when(cart).setItems(Mockito.<List<Items>>any());
        doNothing().when(cart).setTotalPrice(anyDouble());
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);

        // Act
        double actualCartTotalResult = cartServiceImpl.cartTotal(cart);

        // Assert
        verify(cart).getTotalPrice();
        verify(cart).setCartId(eq("42"));
        verify(cart).setItems(isA(List.class));
        verify(cart).setTotalPrice(eq(10.0d));
        assertEquals(10.0d, actualCartTotalResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#addCart(Cart)}
     */
    @Test
    void testAddCart() throws CartAlreadyExistsException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setTotalPrice(10.0d);

        // Act
        Cart actualAddCartResult = cartServiceImpl.addCart(cart2);

        // Assert
        verify(cartRepository).save(isA(Cart.class));
        assertSame(cart, actualAddCartResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#addCart(Cart)}
     */
    @Test
    void testAddCart2() throws CartAlreadyExistsException {
        // Arrange
        when(cartRepository.save(Mockito.<Cart>any())).thenThrow(new CartNotFoundException("An error occurred"));

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.addCart(cart));
        verify(cartRepository).save(isA(Cart.class));
    }

    /**
     * Method under test: {@link CartServiceImpl#deleteCartById(String)}
     */
    @Test
    void testDeleteCartById() {
        // Arrange
        doNothing().when(cartRepository).deleteById(Mockito.<String>any());

        // Act
        String actualDeleteCartByIdResult = cartServiceImpl.deleteCartById("42");

        // Assert
        verify(cartRepository).deleteById(eq("42"));
        assertEquals("Deleted Successfully", actualDeleteCartByIdResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#deleteCartById(String)}
     */
    @Test
    void testDeleteCartById2() {
        // Arrange
        doThrow(new CartNotFoundException("An error occurred")).when(cartRepository).deleteById(Mockito.<String>any());

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.deleteCartById("42"));
        verify(cartRepository).deleteById(eq("42"));
    }

    /**
     * Method under test: {@link CartServiceImpl#addProductToCart(String, String)}
     */
    @Test
    void testAddProductToCart() throws RestClientException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating(10.0d);
        product.setReview("Review");
        product.setSpecification("Specification");
        when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<Product>>any(), isA(Object[].class)))
                .thenReturn(product);

        // Act
        Cart actualAddProductToCartResult = cartServiceImpl.addProductToCart("42", "42");

        // Assert
        verify(cartRepository).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        verify(restTemplate).getForObject(eq("http://localhost:3334/product/42"), isA(Class.class), isA(Object[].class));
        assertSame(cart2, actualAddProductToCartResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#addProductToCart(String, String)}
     */
    @Test
    void testAddProductToCart2() throws RestClientException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenThrow(new CartNotFoundException("An error occurred"));
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating(10.0d);
        product.setReview("Review");
        product.setSpecification("Specification");
        when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<Product>>any(), isA(Object[].class)))
                .thenReturn(product);

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.addProductToCart("42", "42"));
        verify(cartRepository).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        verify(restTemplate).getForObject(eq("http://localhost:3334/product/42"), isA(Class.class), isA(Object[].class));
    }

    /**
     * Method under test: {@link CartServiceImpl#addProductToCart(String, String)}
     */
    @Test
    void testAddProductToCart3() throws RestClientException {
        // Arrange
        ArrayList<Items> items = new ArrayList<>();
        items.add(new Items("42", "Product not in the cart. Adding as a new item.", 10.0d, 1,
                "Product not in the cart. Adding as a new item."));

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(items);
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating(10.0d);
        product.setReview("Review");
        product.setSpecification("Specification");
        when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<Product>>any(), isA(Object[].class)))
                .thenReturn(product);

        // Act
        Cart actualAddProductToCartResult = cartServiceImpl.addProductToCart("42", "42");

        // Assert
        verify(cartRepository).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        verify(restTemplate).getForObject(eq("http://localhost:3334/product/42"), isA(Class.class), isA(Object[].class));
        assertSame(cart2, actualAddProductToCartResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#addProductToCart(String, String)}
     */
    @Test
    void testAddProductToCart4() throws RestClientException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(false);

        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating(10.0d);
        product.setReview("Review");
        product.setSpecification("Specification");
        when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<Product>>any(), isA(Object[].class)))
                .thenReturn(product);

        // Act
        Cart actualAddProductToCartResult = cartServiceImpl.addProductToCart("42", "42");

        // Assert
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        verify(restTemplate).getForObject(eq("http://localhost:3334/product/42"), isA(Class.class), isA(Object[].class));
        assertSame(cart, actualAddProductToCartResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#deleteCartItem(String, String)}
     */
    @Test
    void testDeleteCartItem() throws CartNotFoundException, RestClientException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        ArrayList<Items> items = new ArrayList<>();
        cart2.setItems(items);
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating(10.0d);
        product.setReview("Review");
        product.setSpecification("Specification");
        when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<Product>>any(), isA(Object[].class)))
                .thenReturn(product);

        // Act
        Cart actualDeleteCartItemResult = cartServiceImpl.deleteCartItem("42", "42");

        // Assert
        verify(cartRepository, atLeast(1)).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        verify(restTemplate).getForObject(eq("http://localhost:3334/product/42"), isA(Class.class), isA(Object[].class));
        assertEquals("42", actualDeleteCartItemResult.getCartId());
        assertEquals(0.0d, actualDeleteCartItemResult.getTotalPrice());
        assertEquals(items, actualDeleteCartItemResult.getItems());
        assertSame(cart, actualDeleteCartItemResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#deleteCartItem(String, String)}
     */
    @Test
    void testDeleteCartItem2() throws CartNotFoundException, RestClientException {
        // Arrange
        when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<Product>>any(), isA(Object[].class)))
                .thenThrow(new CartNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.deleteCartItem("42", "42"));
        verify(restTemplate).getForObject(eq("http://localhost:3334/product/42"), isA(Class.class), isA(Object[].class));
    }

    /**
     * Method under test: {@link CartServiceImpl#deleteCartItem(String, String)}
     */
    @Test
    void testDeleteCartItem3() throws CartNotFoundException, RestClientException {
        // Arrange
        ArrayList<Items> items = new ArrayList<>();
        items.add(new Items("42", "Product Name", 10.0d, 1, "Image"));

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(items);
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        ArrayList<Items> items2 = new ArrayList<>();
        cart2.setItems(items2);
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating(10.0d);
        product.setReview("Review");
        product.setSpecification("Specification");
        when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<Product>>any(), isA(Object[].class)))
                .thenReturn(product);

        // Act
        Cart actualDeleteCartItemResult = cartServiceImpl.deleteCartItem("42", "42");

        // Assert
        verify(cartRepository, atLeast(1)).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        verify(restTemplate).getForObject(eq("http://localhost:3334/product/42"), isA(Class.class), isA(Object[].class));
        assertEquals("42", actualDeleteCartItemResult.getCartId());
        assertEquals(0.0d, actualDeleteCartItemResult.getTotalPrice());
        List<Items> items3 = actualDeleteCartItemResult.getItems();
        assertTrue(items3.isEmpty());
        assertEquals(items2, items3);
        assertSame(cart, actualDeleteCartItemResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#deleteCartItem(String, String)}
     */
    @Test
    void testDeleteCartItem4() throws CartNotFoundException, RestClientException {
        // Arrange
        ArrayList<Items> items = new ArrayList<>();
        items.add(new Items("42", "42", 10.0d, 1, "42"));
        items.add(new Items("42", "Product Name", 10.0d, 1, "Image"));

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(items);
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating(10.0d);
        product.setReview("Review");
        product.setSpecification("Specification");
        when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<Product>>any(), isA(Object[].class)))
                .thenReturn(product);

        // Act
        Cart actualDeleteCartItemResult = cartServiceImpl.deleteCartItem("42", "42");

        // Assert
        verify(cartRepository, atLeast(1)).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        verify(restTemplate).getForObject(eq("http://localhost:3334/product/42"), isA(Class.class), isA(Object[].class));
        assertEquals("42", actualDeleteCartItemResult.getCartId());
        assertEquals(1, actualDeleteCartItemResult.getItems().size());
        assertEquals(10.0d, actualDeleteCartItemResult.getTotalPrice());
        assertSame(cart, actualDeleteCartItemResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#deleteCartItem(String, String)}
     */
    @Test
    void testDeleteCartItem5() throws CartNotFoundException, RestClientException {
        // Arrange
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(false);

        Product product = new Product();
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setImage("Image");
        product.setPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setProductType("Product Type");
        product.setRating(10.0d);
        product.setReview("Review");
        product.setSpecification("Specification");
        when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<Product>>any(), isA(Object[].class)))
                .thenReturn(product);

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.deleteCartItem("42", "42"));
        verify(cartRepository).existsById(eq("42"));
        verify(restTemplate).getForObject(eq("http://localhost:3334/product/42"), isA(Class.class), isA(Object[].class));
    }

    /**
     * Method under test: {@link CartServiceImpl#decreaseItem(String, String)}
     */
    @Test
    void testDecreaseItem() throws CartNotFoundException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        ArrayList<Items> items = new ArrayList<>();
        cart2.setItems(items);
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        // Act
        Cart actualDecreaseItemResult = cartServiceImpl.decreaseItem("42", "42");

        // Assert
        verify(cartRepository, atLeast(1)).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        assertEquals("42", actualDecreaseItemResult.getCartId());
        assertEquals(0.0d, actualDecreaseItemResult.getTotalPrice());
        assertEquals(items, actualDecreaseItemResult.getItems());
        assertSame(cart, actualDecreaseItemResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#decreaseItem(String, String)}
     */
    @Test
    void testDecreaseItem2() throws CartNotFoundException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenThrow(new CartNotFoundException("An error occurred"));
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.decreaseItem("42", "42"));
        verify(cartRepository, atLeast(1)).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
    }

    /**
     * Method under test: {@link CartServiceImpl#decreaseItem(String, String)}
     */
    @Test
    void testDecreaseItem3() throws CartNotFoundException {
        // Arrange
        ArrayList<Items> items = new ArrayList<>();
        items.add(new Items("42", "Attempting to decrease quantity for product with ID {} in cart with ID {}", 10.0d, 1,
                "Attempting to decrease quantity for product with ID {} in cart with ID {}"));

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(items);
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        ArrayList<Items> items2 = new ArrayList<>();
        cart2.setItems(items2);
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        // Act
        Cart actualDecreaseItemResult = cartServiceImpl.decreaseItem("42", "42");

        // Assert
        verify(cartRepository, atLeast(1)).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        assertEquals("42", actualDecreaseItemResult.getCartId());
        assertEquals(0.0d, actualDecreaseItemResult.getTotalPrice());
        List<Items> items3 = actualDecreaseItemResult.getItems();
        assertTrue(items3.isEmpty());
        assertEquals(items2, items3);
        assertSame(cart, actualDecreaseItemResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#decreaseItem(String, String)}
     */
    @Test
    void testDecreaseItem4() throws CartNotFoundException {
        // Arrange
        ArrayList<Items> items = new ArrayList<>();
        items.add(new Items("42", "Attempting to decrease quantity for product with ID {} in cart with ID {}", 10.0d, 1,
                "Attempting to decrease quantity for product with ID {} in cart with ID {}"));
        items.add(new Items("42", "Attempting to decrease quantity for product with ID {} in cart with ID {}", 10.0d, 1,
                "Attempting to decrease quantity for product with ID {} in cart with ID {}"));

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(items);
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        // Act
        Cart actualDecreaseItemResult = cartServiceImpl.decreaseItem("42", "42");

        // Assert
        verify(cartRepository, atLeast(1)).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        assertEquals("42", actualDecreaseItemResult.getCartId());
        assertEquals(1, actualDecreaseItemResult.getItems().size());
        assertEquals(10.0d, actualDecreaseItemResult.getTotalPrice());
        assertSame(cart, actualDecreaseItemResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#decreaseItem(String, String)}
     */
    @Test
    void testDecreaseItem5() throws CartNotFoundException {
        // Arrange
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(false);

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.decreaseItem("42", "42"));
        verify(cartRepository).existsById(eq("42"));
    }

    /**
     * Method under test: {@link CartServiceImpl#decreaseItem(String, String)}
     */
    @Test
    void testDecreaseItem6() throws CartNotFoundException {
        // Arrange
        Items items = mock(Items.class);
        doNothing().when(items).setQuantity(anyInt());
        when(items.getPrice()).thenReturn(10.0d);
        when(items.getQuantity()).thenReturn(1);
        when(items.getProductId()).thenReturn("42");

        ArrayList<Items> items2 = new ArrayList<>();
        items2.add(items);

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(items2);
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        // Act
        Cart actualDecreaseItemResult = cartServiceImpl.decreaseItem("42", "42");

        // Assert
        verify(items).getPrice();
        verify(items).getProductId();
        verify(items, atLeast(1)).getQuantity();
        verify(items).setQuantity(eq(0));
        verify(cartRepository, atLeast(1)).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        assertEquals("42", actualDecreaseItemResult.getCartId());
        assertEquals(1, actualDecreaseItemResult.getItems().size());
        assertEquals(10.0d, actualDecreaseItemResult.getTotalPrice());
        assertSame(cart, actualDecreaseItemResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#decreaseItem(String, String)}
     */
    @Test
    void testDecreaseItem7() throws CartNotFoundException {
        // Arrange
        Items items = mock(Items.class);
        doThrow(new CartNotFoundException("An error occurred")).when(items).setQuantity(anyInt());
        when(items.getQuantity()).thenReturn(1);
        when(items.getProductId()).thenReturn("42");

        ArrayList<Items> items2 = new ArrayList<>();
        items2.add(items);

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(items2);
        cart.setTotalPrice(10.0d);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        // Act and Assert
        assertThrows(CartNotFoundException.class, () -> cartServiceImpl.decreaseItem("42", "42"));
        verify(items).getProductId();
        verify(items).getQuantity();
        verify(items).setQuantity(eq(0));
        verify(cartRepository).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
    }

    /**
     * Method under test: {@link CartServiceImpl#decreaseItem(String, String)}
     */
    @Test
    void testDecreaseItem8() throws CartNotFoundException {
        // Arrange
        Items items = mock(Items.class);
        when(items.getPrice()).thenReturn(10.0d);
        when(items.getQuantity()).thenReturn(0);
        when(items.getProductId()).thenReturn("42");

        ArrayList<Items> items2 = new ArrayList<>();
        items2.add(items);

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(items2);
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        // Act
        Cart actualDecreaseItemResult = cartServiceImpl.decreaseItem("42", "42");

        // Assert
        verify(items).getPrice();
        verify(items).getProductId();
        verify(items, atLeast(1)).getQuantity();
        verify(cartRepository, atLeast(1)).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        assertEquals("42", actualDecreaseItemResult.getCartId());
        assertEquals(0.0d, actualDecreaseItemResult.getTotalPrice());
        assertEquals(1, actualDecreaseItemResult.getItems().size());
        assertSame(cart, actualDecreaseItemResult);
    }

    /**
     * Method under test: {@link CartServiceImpl#decreaseItem(String, String)}
     */
    @Test
    void testDecreaseItem9() throws CartNotFoundException {
        // Arrange
        Items items = mock(Items.class);
        when(items.getPrice()).thenReturn(10.0d);
        when(items.getQuantity()).thenReturn(1);
        when(items.getProductId()).thenReturn("Attempting to decrease quantity for product with ID {} in cart with ID {}");

        ArrayList<Items> items2 = new ArrayList<>();
        items2.add(items);

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(items2);
        cart.setTotalPrice(10.0d);

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setTotalPrice(10.0d);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        when(cartRepository.existsById(Mockito.<String>any())).thenReturn(true);

        // Act
        Cart actualDecreaseItemResult = cartServiceImpl.decreaseItem("42", "42");

        // Assert
        verify(items).getPrice();
        verify(items).getProductId();
        verify(items).getQuantity();
        verify(cartRepository, atLeast(1)).findByCartId(eq("42"));
        verify(cartRepository).existsById(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        assertEquals("42", actualDecreaseItemResult.getCartId());
        assertEquals(1, actualDecreaseItemResult.getItems().size());
        assertEquals(10.0d, actualDecreaseItemResult.getTotalPrice());
        assertSame(cart, actualDecreaseItemResult);
    }
}
