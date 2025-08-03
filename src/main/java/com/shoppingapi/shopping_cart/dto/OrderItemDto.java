package com.shoppingapi.shopping_cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDto {
    private ProductItem product;
    private int quantity;
    private BigDecimal totalPrice;
}
