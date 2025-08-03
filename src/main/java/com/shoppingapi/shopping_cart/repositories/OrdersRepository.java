package com.shoppingapi.shopping_cart.repositories;

import com.shoppingapi.shopping_cart.entities.Order;
import com.shoppingapi.shopping_cart.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Order, Long> {

//    List <Order> getByCustomerId(Long customerId);
    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order o where o.customer = :customer")
    List<Order> getAllByCustomer(@Param("customer") User customer);

    @EntityGraph(attributePaths = "items.product")
    Order getOrderById(Long orderId);

    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order o WHERE o.id = :orderId")
    Optional<Order> getOrderWithItems(@Param("orderId")Long orderId);
}