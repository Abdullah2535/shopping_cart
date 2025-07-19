package com.shoppingapi.shopping_cart.repositories;

import com.shoppingapi.shopping_cart.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}