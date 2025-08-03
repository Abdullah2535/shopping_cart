package com.shoppingapi.shopping_cart.cofig;


import com.shoppingapi.shopping_cart.entities.Role;
import com.shoppingapi.shopping_cart.filters.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    public final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
//@Bean
//public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
//                                                   PasswordEncoder passwordEncoder) {
//    return authentication -> {
//        UserDetails user = userDetailsService.loadUserByUsername(authentication.getName());
//        if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
//            throw new BadCredentialsException("Invalid credentials");
//        }
//        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
//    };
//}


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Stateless sessions (token-based authentication)
        //Disable CSRF
        //Authorize
        http
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        c -> c.
                                requestMatchers("/cart/**").permitAll()
                                .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST,"/users").permitAll()
                                .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST,"/auth/refresh").permitAll()
                                .requestMatchers(HttpMethod.POST,"orders/webhook").permitAll()

                                .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(c->{
                            c.authenticationEntryPoint(
                                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                            c.accessDeniedHandler(((request, response, accessDeniedException) ->
                                    response.setStatus(HttpStatus.FORBIDDEN.value()  )));
                        }
                        );
        return http.build();
    }
//1245786 admin password
}

