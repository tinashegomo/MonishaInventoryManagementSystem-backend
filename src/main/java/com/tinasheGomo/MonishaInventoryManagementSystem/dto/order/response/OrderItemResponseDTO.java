package com.tinasheGomo.MonishaInventoryManagementSystem.dto.order.response;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.measurement.MeasurementResponseDTO;
import java.math.BigDecimal; import java.time.LocalDateTime; import java.util.List; import java.util.UUID;
public record OrderItemResponseDTO(
    UUID orderItemId, String type, String variant, String color,
    String size, Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice,
    Boolean customMade, Boolean measurementsTaken,
    UUID productId, UUID batchId,
    LocalDateTime createdAt,
    List<MeasurementResponseDTO> measurements
) {}
