package com.shoppingapi.shopping_cart.repositories;

import com.shoppingapi.shopping_cart.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
    Category getById(Byte id);
}