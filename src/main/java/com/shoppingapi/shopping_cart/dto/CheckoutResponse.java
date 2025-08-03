package com.shoppingapi.shopping_cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutResponse {
    private Long orderId;
    private String checkoutUrl;

    public CheckoutResponse(Long id, String checkoutUrl) {
        this.orderId = id;
        this.checkoutUrl = checkoutUrl;
    }
}
