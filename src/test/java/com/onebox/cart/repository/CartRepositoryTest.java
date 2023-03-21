package com.onebox.cart.repository;
import com.onebox.cart.model.Cart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataMongoTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testFindByDateUpdateBefore() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<Cart> carts = cartRepository.findByDateUpdateBefore(dateTime);
        Assertions.assertNotNull(carts);
    }

    @Test
    public void testFindById() {
        Cart cart = new Cart();
        cart.setId("cartId");
        cartRepository.save(cart);

        Optional<Cart> optionalCart = cartRepository.findById("cartId");
        Assertions.assertTrue(optionalCart.isPresent());
    }
}
