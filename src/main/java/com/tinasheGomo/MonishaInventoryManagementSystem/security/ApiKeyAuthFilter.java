package com.tinasheGomo.MonishaInventoryManagementSystem.security;

import com.tinasheGomo.MonishaInventoryManagementSystem.entity.user.UserEntity;
import com.tinasheGomo.MonishaInventoryManagementSystem.enums.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final ServiceApiKeyConfig apiKeyConfig;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        // Only intercept /api/public/imsClient/** paths
        if (!path.startsWith("/api/public/imsClient/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Read the API key from the request header
        String apiKey = request.getHeader("X-Internal-Api-Key");

        if (apiKey == null || apiKey.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Missing X-Internal-Api-Key header\"}");
            return;
        }

        // Match the key against configured scoped keys
        String authority = resolveAuthority(apiKey);

        if (authority == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Invalid or unauthorized API key\"}");
            return;
        }

        // Create a fake UserEntity for SecurityUtils compatibility
        // This allows OrderService.createOrder() to call SecurityUtils.getCurrentUser()
        // without modification when processing ecom orders
        UserEntity serviceUser = new UserEntity();
        serviceUser.setUserName("ECOM_ORDER");
        serviceUser.setUserEmail("ecom-api@monisha.local");
        serviceUser.setUserRole(UserRole.USER);

        AuthUser authUser = new AuthUser(serviceUser);

        // Set authenticated context with the matched authority
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        authUser,
                        null,
                        List.of(new SimpleGrantedAuthority(authority))
                );

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    /**
     * Resolves the authority based on the API key.
     * Returns null if the key is invalid.
     */
    private String resolveAuthority(String apiKey) {

        if (apiKey.equals(apiKeyConfig.getEcomCatalogKey())) {
            return "ROLE_SERVICE_CATALOG";
        }

        if (apiKey.equals(apiKeyConfig.getEcomCustomersKey())) {
            return "ROLE_SERVICE_CUSTOMERS";
        }

        if (apiKey.equals(apiKeyConfig.getEcomOrdersKey())) {
            return "ROLE_SERVICE_ORDERS";
        }

        return null;
    }
}
