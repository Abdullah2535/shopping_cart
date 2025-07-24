package com.shoppingapi.shopping_cart.Controllers;

import com.shoppingapi.shopping_cart.Services.JwtService;
import com.shoppingapi.shopping_cart.dto.JwtResponse;
import com.shoppingapi.shopping_cart.dto.LoginRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
      var token =  jwtService.generateToken(request.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void>handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @PostMapping("/validate")
    public boolean validate(@RequestHeader("Authorization") String authHeader){
        System.out.println("Validate called" );
     var token = authHeader.replace("Bearer ", "");
      return jwtService.validateToken(token);
    }
}
