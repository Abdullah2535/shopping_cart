package com.shoppingapi.shopping_cart.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * DTO for {@link com.shoppingapi.shopping_cart.entities.Product}
 */
@Data
public class CartProductDto  {
   private Long id;
    private String name;
    private BigDecimal price;
}