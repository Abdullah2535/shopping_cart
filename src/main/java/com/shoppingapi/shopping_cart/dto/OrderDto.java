package com.shoppingapi.shopping_cart.dto;

import com.shoppingapi.shopping_cart.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private Set<OrderItemDto> items;
    private BigDecimal totalPrice;
}
