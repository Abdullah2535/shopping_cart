package com.shoppingapi.shopping_cart.Services;

import com.shoppingapi.shopping_cart.entities.User;
import com.shoppingapi.shopping_cart.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    public final UserRepository userRepository;
    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId= (Long) authentication.getPrincipal();
        return userRepository.findById(userId).orElse(null);
    }

}
