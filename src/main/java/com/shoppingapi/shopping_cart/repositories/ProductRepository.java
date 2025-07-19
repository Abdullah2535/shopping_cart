package com.shoppingapi.shopping_cart.repositories;


import com.shoppingapi.shopping_cart.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

   @EntityGraph(attributePaths = "category")
   public List<Product> findByCategoryId(Byte categoryId);

   @EntityGraph(attributePaths = "category")
   @Query("SELECT p from Product p")
   List<Product> findAllWithCategory();


}