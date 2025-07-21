package com.shoppingapi.shopping_cart.dto;


import lombok.Data;
import java.math.BigDecimal;
import java.util.*;

@Data
public class CartDto {
    private UUID id;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice;

}
