package com.tinasheGomo.MonishaInventoryManagementSystem.dto.order.response;
import com.tinasheGomo.MonishaInventoryManagementSystem.enums.OrderStatus;
import com.tinasheGomo.MonishaInventoryManagementSystem.enums.PaymentType;
import java.math.BigDecimal; import java.time.LocalDate; import java.time.LocalDateTime; import java.util.List; import java.util.UUID;
public record OrderResponseDTO(
    UUID orderId, String orderNumber,
    UUID customerId, String customerName, String customerPhone,
    UUID schoolId, String schoolName,
    BigDecimal totalAmount, BigDecimal paidAmount, BigDecimal balance, Boolean fullyPaid,
    PaymentType paymentType,
    Boolean hasMeasurements, Boolean schoolOrder,
    OrderStatus orderStatus,
    LocalDate collectionDate, String notes, String createdBy,
    LocalDateTime createdAt, LocalDateTime updatedAt,
    List<OrderItemResponseDTO> orderItems
) {}
