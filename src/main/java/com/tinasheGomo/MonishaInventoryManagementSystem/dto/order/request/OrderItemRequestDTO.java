package com.tinasheGomo.MonishaInventoryManagementSystem.dto.order.request;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.measurement.MeasurementRequestDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderItemRequestDTO(
    UUID productId,
    UUID batchId,
    String size,
    @NotNull(message = "Quantity is required") @Min(value = 1, message = "Quantity must be at least 1") Integer quantity,
    String type,
    String variant,
    String color,
    BigDecimal unitPrice,
    Boolean customMade,
    Boolean measurementsTaken,
    List<MeasurementRequestDTO> measurements
) {}
