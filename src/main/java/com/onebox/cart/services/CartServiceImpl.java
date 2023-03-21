package com.onebox.cart.services;

import com.onebox.cart.dto.CartDto;
import com.onebox.cart.exception.CartNotFound;
import com.onebox.cart.exception.EmptyCartException;
import com.onebox.cart.exception.ProductNotFoundException;
import com.onebox.cart.model.Cart;
import com.onebox.cart.model.Product;
import com.onebox.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private ProductService productService;

    @Override
    @Transactional
    public Cart create(CartDto cartRequest) {
        handleIfCartHasNotProducts(cartRequest);
        var products = filterProductsIfExists(cartRequest);
        var totalAmount = getTotalAmount(products);

        Cart cart = new Cart();
        cart.setProducts(products);
        cart.setDateUpdate(LocalDateTime.now());
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    /**
     * Handles if the cart has products if it does not return an error
     *
     * @param cartRequest
     */
    private void handleIfCartHasNotProducts(CartDto cartRequest) {
        if (cartRequest.getProducts() == null || CollectionUtils.isEmpty(cartRequest.getProducts())) {
            log.error("Error when creating the cart the cart does not have products ");
            throw new EmptyCartException("Cannot create or update an empty cart");
        }
    }

    private static BigDecimal getTotalAmount(List<Product> products) {
        return products.stream()
                .map(Product::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Filter the products in the shopping cart and check if they exist in the database
     *
     * @param cartRequest
     * @return
     */
    private List<Product> filterProductsIfExists(CartDto cartRequest) {
        return cartRequest.getProducts().stream()
                .map(productDto -> {
                    var existingProduct = productService.getProductById(productDto.getId());
                    if (existingProduct != null) {
                        existingProduct.setQuantity(productDto.getQuantity());
                        return existingProduct;
                    } else {
                        log.error("Error retrieving product with id {}", productDto.getId());
                        throw new ProductNotFoundException("Product not found with ID: " + productDto.getId());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cart> findById(String id) {
        return cartRepository.findById(id);
    }

    @Override
    public void deleteCartById(String id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart update(CartDto cartDto) {
        handleIfCartExists(cartDto);
        handleIfCartHasNotProducts(cartDto);
        var products = filterProductsIfExists(cartDto);
        var totalAmount = getTotalAmount(products);
        var cart = new Cart(cartDto.getId(), products, LocalDateTime.now(), totalAmount);
        return cartRepository.save(cart);
    }

    /**
     * Check if the cart exists if it does not return an error
     *
     * @param cartDto
     */
    private void handleIfCartExists(CartDto cartDto) {
        var cart = findById(cartDto.getId());
        if (!cart.isPresent()) {
            log.error("Could not find product with id {}", cartDto.getId());
            throw new CartNotFound("No cart found with this id " + cartDto.getId());
        }
    }
}
