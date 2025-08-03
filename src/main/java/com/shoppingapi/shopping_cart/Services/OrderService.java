package com.shoppingapi.shopping_cart.Services;

import com.shoppingapi.shopping_cart.dto.CheckoutRequest;
import com.shoppingapi.shopping_cart.dto.CheckoutResponse;
import com.shoppingapi.shopping_cart.dto.OrderDto;
import com.shoppingapi.shopping_cart.entities.Order;
import com.shoppingapi.shopping_cart.exceptions.CartEmptyException;
import com.shoppingapi.shopping_cart.exceptions.CartNotFoundException;
import com.shoppingapi.shopping_cart.exceptions.OrderNotFoundException;
import com.shoppingapi.shopping_cart.exceptions.PaymentException;
import com.shoppingapi.shopping_cart.repositories.CartRepository;
import com.shoppingapi.shopping_cart.repositories.OrdersRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final AuthService authentication;
    private final CartRepository cartRepository;
    private final OrdersRepository ordersRepository;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;



    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request)  {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null  ) {
            throw new CartNotFoundException();
        }
        if (cart.isEmpty()){
            throw new CartEmptyException();
        }
        var order = Order.createNewOrder(authentication.getCurrentUser(), cart);
        ordersRepository.save(order);
        try {
            var session = paymentGateway.createCheckoutSession(order);

            cartService.removeAllCartItems(cart.getId());
            return new CheckoutResponse(order.getId(),session.getCheckoutUrl());
        } catch (PaymentException e) {
            System.out.println(e.getMessage());
            ordersRepository.delete(order);
            throw e;
        }
    }
    public List <OrderDto> getLoggedUserOrders(){
        var ordersList = new ArrayList<OrderDto>();
        var user = authentication.getCurrentUser();
        var orders = ordersRepository.getAllByCustomer(user);
        if (orders.isEmpty()){
            return null;
        }
        for (Order order : orders) {
           ordersList.add( order.createOrderDto(order));
        }
        return ordersList;
    }
    public OrderDto getOrder(Long id){
        var order =ordersRepository.getOrderWithItems(id)
                .orElseThrow(OrderNotFoundException::new);
        var user =authentication.getCurrentUser();
        if (!order.isPlacedBy(user)){
            throw new AccessDeniedException("You don't have access to this order");
        }
      return order.createOrderDto(order);
    }

   public void handleWebhookEvent( WebhookRequest webhookRequest) {
       paymentGateway
               .parseWebhookRequest(webhookRequest)
               .ifPresent(paymentResult -> {
                           var order = ordersRepository.findById(paymentResult.getOrderId()).orElseThrow();
                           order.setStatus(paymentResult.getPaymnetStatus());
                           ordersRepository.save(order);
                       });
   }

    }
