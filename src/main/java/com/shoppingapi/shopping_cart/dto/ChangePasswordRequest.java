package com.shoppingapi.shopping_cart.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String  oldPassword;
    private String  newPassword;
}
