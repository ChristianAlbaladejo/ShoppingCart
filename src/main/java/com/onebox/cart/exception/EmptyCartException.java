package com.onebox.cart.exception;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException(String errorMessage) {
        super(errorMessage);
    }
}
