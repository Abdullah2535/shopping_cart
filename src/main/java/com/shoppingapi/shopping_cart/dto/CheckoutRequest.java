package com.shoppingapi.shopping_cart.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CheckoutRequest {
   @NotNull(message = "Cart ID is required. ")
   private UUID cartId;
}
