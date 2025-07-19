package com.shoppingapi.shopping_cart.repositories;

import com.shoppingapi.shopping_cart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

}
