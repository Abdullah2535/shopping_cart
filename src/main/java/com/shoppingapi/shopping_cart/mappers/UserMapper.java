package com.shoppingapi.shopping_cart.mappers;

import com.shoppingapi.shopping_cart.dto.RegisterUserRequest;
import com.shoppingapi.shopping_cart.dto.UpdateUserRequest;
import com.shoppingapi.shopping_cart.dto.UserDto;
import com.shoppingapi.shopping_cart.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // @Mapping(target = "createdAt",expression = "java(java.time.LocalDateTime.now())")
    UserDto toUserDto(User user);

    User toEntity(RegisterUserRequest request);
//
    void updateUser(UpdateUserRequest request, @MappingTarget User user);
}
