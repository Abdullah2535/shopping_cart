package com.shoppingapi.shopping_cart.Services;

import com.shoppingapi.shopping_cart.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResult {
    private Long orderId;
    private PaymentStatus paymnetStatus;
}
