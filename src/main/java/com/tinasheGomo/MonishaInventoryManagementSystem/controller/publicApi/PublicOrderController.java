package com.tinasheGomo.MonishaInventoryManagementSystem.controller.publicApi;

import com.tinasheGomo.MonishaInventoryManagementSystem.dto.order.request.OrderRequestDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.order.response.OrderResponseDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/imsClient/orders")
@RequiredArgsConstructor
public class PublicOrderController {

    private final OrderService orderService;

    /**
     * Create an order from the ecom backend.
     * Delegates to the same OrderService.createOrder() method
     * that the staff counter flow uses — same transaction boundary,
     * same stock check, same order number generation.
     */
    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody @Valid OrderRequestDTO requestDTO) {
        return orderService.createOrder(requestDTO);
    }

    /**
     * Get order history for a specific customer.
     * Used by the ecom backend to display "My Orders".
     */
    @GetMapping("/customer/{customerId}")
    public List<OrderResponseDTO> getOrdersByCustomerId(@PathVariable UUID customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }
}
