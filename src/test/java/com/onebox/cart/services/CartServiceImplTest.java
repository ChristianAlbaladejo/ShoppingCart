package com.onebox.cart.services;

import com.onebox.cart.dto.CartDto;
import com.onebox.cart.dto.ProductDto;
import com.onebox.cart.model.Cart;
import com.onebox.cart.model.Product;
import com.onebox.cart.repository.CartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductService productService;

    private CartServiceImpl cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.cartService = new CartServiceImpl(cartRepository, productService);
    }

    @Test
    public void testCreateOrUpdateCart() {
        CartDto cartRequest = new CartDto();
        cartRequest.setId(UUID.randomUUID().toString());
        List<ProductDto> products = new ArrayList<>();
        ProductDto product1 = new ProductDto();
        product1.setId(UUID.randomUUID().toString());
        product1.setQuantity(2);
        products.add(product1);
        cartRequest.setProducts(products);

        Product product = new Product();
        product.setId(product1.getId());
        product.setDescription("Test product");
        product.setAmount(new BigDecimal("10.00"));
        when(productService.getProductById(product1.getId())).thenReturn(product);

        Cart savedCart = new Cart();
        savedCart.setId(cartRequest.getId());
        savedCart.setProducts(List.of(product));
        savedCart.setDateUpdate(LocalDateTime.now());
        savedCart.setTotalAmount(new BigDecimal("20.00"));
        when(cartRepository.save(any(Cart.class))).thenReturn(savedCart);

        Cart createdCart = cartService.create(cartRequest);
        Assertions.assertEquals(savedCart.getId(), createdCart.getId());
        Assertions.assertEquals(savedCart.getProducts(), createdCart.getProducts());
        Assertions.assertEquals(savedCart.getDateUpdate(), createdCart.getDateUpdate());
        Assertions.assertEquals(savedCart.getTotalAmount(), createdCart.getTotalAmount());
    }

    @Test
    public void testFindById() {
        String cartId = UUID.randomUUID().toString();
        Cart expectedCart = new Cart();
        expectedCart.setId(cartId);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(expectedCart));

        Optional<Cart> foundCart = cartService.findById(cartId);
        Assertions.assertTrue(foundCart.isPresent());
        Assertions.assertEquals(expectedCart, foundCart.get());
    }

    @Test
    public void testDeleteCartById() {
        String cartId = UUID.randomUUID().toString();
        cartService.deleteCartById(cartId);
        Mockito.verify(cartRepository).deleteById(cartId);
    }

}
