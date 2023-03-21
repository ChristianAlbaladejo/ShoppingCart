package com.onebox.cart.services;

import com.onebox.cart.dto.CartDto;
import com.onebox.cart.model.Cart;

import java.util.Optional;

public interface CartService {
    /**
     * Create a cart
     *
     * @param cartRequest
     * @return
     */
    Cart create(CartDto cartRequest);

    /**
     * Search for a cart by id
     *
     * @param id
     * @return
     */
    Optional<Cart> findById(String id);

    /**
     * Delete a cart by id
     *
     * @param id
     */
    void deleteCartById(String id);

    /**
     * Update saved cart
     *
     * @param cartDto
     * @return
     */
    Cart update(CartDto cartDto);
}
