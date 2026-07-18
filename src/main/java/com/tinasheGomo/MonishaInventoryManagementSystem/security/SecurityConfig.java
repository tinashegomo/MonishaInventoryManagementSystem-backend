package com.tinasheGomo.MonishaInventoryManagementSystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // Service that loads user from database
    private final CustomUserDetailsService userDetailsService;

    // Our JWT filter for staff auth
    private final AuthFilter authFilter;

    // API key filter for ecom server-to-server calls
    private final ApiKeyAuthFilter apiKeyAuthFilter;

    /*
     Password Encoder

     This is used to hash passwords before saving them to database.

     Example:
     password = "123456"

     Stored in DB:
     $2a$10$QJm....
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     Authentication Provider

     This tells Spring Security HOW to authenticate a user.

     It uses:
     - userDetailsService (to load user from DB)
     - passwordEncoder (to compare passwords)
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        // Tell Spring where to load users from
        provider.setUserDetailsService(userDetailsService);

        // Tell Spring how passwords are encrypted
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    /*
     Authentication Manager

     This is used during login to authenticate credentials.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    /*
     CHAIN 1 — @Order(1) runs first.
     Handles /api/public/imsClient/** paths only.
     Authenticated via X-Internal-Api-Key header (not JWT).
     Scoped keys: ecom-catalog (read products), ecom-customers (create customers), ecom-orders (create/view orders).
     If a request doesn't match /api/public/imsClient/**, it falls through to Chain 2.
    */
    @Bean
    @Order(1)
    public SecurityFilterChain publicApiSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                // Only match paths starting with /api/public/imsClient/
                // All other paths fall through to Chain 2
                .securityMatcher("/api/public/imsClient/**")

                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        // Product catalog — requires catalog key (read-only)
                        .requestMatchers("/api/public/imsClient/products/**").hasRole("SERVICE_CATALOG")

                        // Customer creation — requires customers key
                        .requestMatchers("/api/public/imsClient/customers/**").hasRole("SERVICE_CUSTOMERS")

                        // Order operations — requires orders key
                        .requestMatchers("/api/public/imsClient/orders/**").hasRole("SERVICE_ORDERS")
                )

                // Add API key filter before Spring's default filters
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
     CHAIN 2 — @Order(2) runs second (only if Chain 1 didn't match).
     Handles all staff endpoints under /api/monishaInventory/**.
     Authenticated via JWT in Authorization: Bearer header.
     /auth/** is public (login/register). All other paths require a valid staff JWT.
     Ecom requests never reach this chain — Chain 1 catches them first.
    */
    @Bean
    @Order(2)
    public SecurityFilterChain staffSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {}) // enable CORS using CorsConfig

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        // Staff auth endpoints — no login required
                        .requestMatchers("/api/monishaInventory/auth/**").permitAll()

                        // All other staff endpoints require a valid JWT
                        .anyRequest().authenticated()
                )

                .authenticationProvider(authenticationProvider())

                // Add JWT filter before Spring's default filters
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}