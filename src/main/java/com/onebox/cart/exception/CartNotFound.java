package com.onebox.cart.exception;

public class CartNotFound extends RuntimeException {
    public CartNotFound(String errorMessage) {
        super(errorMessage);
    }
}