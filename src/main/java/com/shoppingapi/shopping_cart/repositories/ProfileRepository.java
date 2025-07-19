package com.shoppingapi.shopping_cart.repositories;

import com.shoppingapi.shopping_cart.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}