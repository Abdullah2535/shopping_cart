package com.shoppingapi.shopping_cart.Controllers;

import com.shoppingapi.shopping_cart.Services.JwtService;
import com.shoppingapi.shopping_cart.cofig.JwtConfig;
import com.shoppingapi.shopping_cart.dto.JwtResponse;
import com.shoppingapi.shopping_cart.dto.LoginRequest;
import com.shoppingapi.shopping_cart.dto.UserDto;
import com.shoppingapi.shopping_cart.mappers.UserMapper;
import com.shoppingapi.shopping_cart.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private JwtService jwtService;
    private final JwtConfig jwtConfig;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request,
                                             HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
       var accessToken =  jwtService.generateAccessToken(user);
        var refreshToken =  jwtService.generateRefreshToken(user);
        var cookie = new Cookie("refresh_token", refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration()); // 7days
        cookie.setSecure(true);
        response.addCookie(cookie);
        System.out.println("Setting refresh token cookie: " + refreshToken);
        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void>handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
//    @PostMapping("/validate")
//    public boolean validate(@RequestHeader("Authorization") String authHeader){
//        System.out.println("Validate called" );
//     var token = authHeader.replace("Bearer ", "");
//      return jwtService.validateToken(authHeader);
//    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken" ) String refreshToken) {
        var jwt = jwtService.parseToken(refreshToken);
        if (jwt == null || jwt.isExpired()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userRepository.findById(jwt.getUserId()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));

    }


    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        var userDto = userMapper.toUserDto(user);
        return ResponseEntity.ok(userDto);


    }
}
