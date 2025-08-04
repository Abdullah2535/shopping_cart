package com.shoppingapi.shopping_cart.payments;


import com.shoppingapi.shopping_cart.dto.ErrorDto;
import com.shoppingapi.shopping_cart.dto.OrderDto;
import com.shoppingapi.shopping_cart.exceptions.CartEmptyException;
import com.shoppingapi.shopping_cart.exceptions.CartNotFoundException;
import com.shoppingapi.shopping_cart.exceptions.OrderNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")

public class OrderController {
    private final OrderService orderService;




    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request) {
            return ResponseEntity.ok(orderService.checkout(request));
    }
    @GetMapping
    public ResponseEntity<List<OrderDto>>getLoggedUserOrders(){
      return ResponseEntity.ok( orderService.getLoggedUserOrders());
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException( ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating a checkout session"));
    }


    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto>handleException(Exception exception) {
        return  ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage()));
    }
    @GetMapping("/{orderId}")
    public OrderDto getLoggedUserOrder(@PathVariable Long orderId){
//        var order = ordersRepository.getOrderById(orderId);
//        if (order == null){
//            return ResponseEntity.notFound().build() ;
//        }
//        var userId = authentication.getCurrentUser().getId();
//        if (!order.getCustomer().getId().equals(userId)){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build() ;
//        }
//        var orderDto = order.createOrderDto(order);
        return orderService.getOrder(orderId);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Void>handleExceptionOrderNotFound(){
        return  ResponseEntity.notFound().build() ;
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto>handleAccessDeniedException(Exception ex){
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(ex.getMessage())) ;
    }
    @PostMapping("/webhook")
    public void handleWebHook(@RequestHeader Map<String,String> headers,
                                             @RequestBody String payload)  {
      orderService.handleWebhookEvent(new WebhookRequest(headers,payload) );
    }




}
