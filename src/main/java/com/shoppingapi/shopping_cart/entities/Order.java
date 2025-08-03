package com.shoppingapi.shopping_cart.entities;

import com.shoppingapi.shopping_cart.dto.OrderDto;
import com.shoppingapi.shopping_cart.dto.OrderItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "order",cascade = {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
    private Set <OrderItem> items = new HashSet<>();

    public static Order createNewOrder(User customer,Cart cart){
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(PaymentStatus.PENDING);
        order.setTotalPrice(cart.getTotalPrice());

        for (var item : cart.getItems()) {
            OrderItem orderItem = new OrderItem(order,item.getProduct(),item.getQuantity());
            order.items.add(orderItem);
        }
        return order;
    }
    public OrderDto createOrderDto(Order order){
        Set<OrderItemDto> orderItemDtos = new HashSet<>();
        for (var item : order.items) {
            orderItemDtos.add(OrderItem.toDto(item));
        }
        return new OrderDto(order.id,order.status,order.createdAt
                ,orderItemDtos
                ,order.totalPrice);
    }
    public boolean isPlacedBy (User customer){
        return this.customer.equals(customer);
    }

}
