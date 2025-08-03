package com.shoppingapi.shopping_cart.Controllers;


import com.shoppingapi.shopping_cart.Services.CartService;
import com.shoppingapi.shopping_cart.dto.AddItemToCartRequest;
import com.shoppingapi.shopping_cart.dto.CartDto;
import com.shoppingapi.shopping_cart.dto.CartItemDto;
import com.shoppingapi.shopping_cart.dto.UpdateCartItemRequest;

import com.shoppingapi.shopping_cart.exceptions.CartNotFoundException;
import com.shoppingapi.shopping_cart.exceptions.ProductNotFoundException;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/cart")
@Tag(name = "carts")
public class CartController {

    private final CartService cartService;

    @PostMapping("")
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
       var cartDto= cartService.createCart();
       var uri= uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
       return  ResponseEntity.created(uri).body(cartDto);
    }
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addProducts(@PathVariable UUID cartId,
                                                   @RequestBody AddItemToCartRequest request
                                                     ) {
        var cartItemDto =  cartService.addToCart(cartId, request.getProductId());
        return   ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }
   @GetMapping("/{id}")
    public CartDto getCart(@PathVariable UUID id) {
       return cartService.getCart(id);

   }

   @PutMapping("{cartId}/items/{productId}")
    public CartItemDto updateCartItem(@PathVariable ("cartId") UUID cartId,
                                                      @PathVariable("productId") Long productId,
                                                      @Valid @RequestBody UpdateCartItemRequest quantity) {
       return  cartService.updateCartItem(cartId ,productId,quantity.getQuantity());
   }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity <Void> removeProduct (@PathVariable UUID cartId,
                                                @PathVariable Long productId) {
        cartService.removeCartItem(cartId ,productId);
        return ResponseEntity.noContent().build();
      }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity <Void> removeAllProducts(@PathVariable UUID cartId) {
        cartService.removeAllCartItems(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({CartNotFoundException.class})
    public ResponseEntity<Map<String,String>> handleCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Cart not found.")
        );
    }
    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<Map<String,String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error", "Product not found in the cart.")
        );
    }

}


