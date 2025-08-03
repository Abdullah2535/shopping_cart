package com.shoppingapi.shopping_cart.exceptions;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException( ) {
        super("Cart is empty");
    }
}
