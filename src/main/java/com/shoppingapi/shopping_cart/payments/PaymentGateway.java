package com.shoppingapi.shopping_cart.payments;

import com.shoppingapi.shopping_cart.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest webhookRequest);
}
