package com.shoppingapi.shopping_cart.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
}
