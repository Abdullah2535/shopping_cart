package com.shoppingapi.shopping_cart.dto;

import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
public class CartItemDto {
    private CartProductDto product;
    private BigDecimal totalPrice;
    private int quantity;
}
