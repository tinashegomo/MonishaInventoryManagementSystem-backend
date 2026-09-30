package com.tinasheGomo.MonishaInventoryManagementSystem.dto.order.request;
import com.tinasheGomo.MonishaInventoryManagementSystem.enums.PaymentType;
import jakarta.validation.constraints.NotEmpty; import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.List; import java.util.UUID;
public record OrderRequestDTO(
    @NotNull(message = "Customer ID is required") UUID customerId,
    UUID schoolId,
    @NotNull(message = "Paid amount is required") BigDecimal paidAmount,
    @NotNull(message = "Payment type is required") PaymentType paymentType,
    LocalDate collectionDate, String notes,
    @NotEmpty(message = "Order must contain at least one item") List<OrderItemRequestDTO> orderItems
) {}
