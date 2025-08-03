package com.shoppingapi.shopping_cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductItem {
    private Long productId;
    private String productName;
    private BigDecimal price;
}
