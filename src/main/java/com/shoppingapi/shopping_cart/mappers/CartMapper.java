package com.shoppingapi.shopping_cart.mappers;


import com.shoppingapi.shopping_cart.dto.CartDto;
import com.shoppingapi.shopping_cart.dto.CartItemDto;
import com.shoppingapi.shopping_cart.entities.Cart;
import com.shoppingapi.shopping_cart.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())" )
    CartDto toDto(Cart cart);
    @Mapping(target = "totalPrice",expression = "java(cartItem.getTotalPrice())" )
    CartItemDto toDto(CartItem cartItem);

}
