package com.shoppingapi.shopping_cart.exceptions;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}
