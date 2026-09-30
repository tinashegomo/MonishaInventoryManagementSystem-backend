package com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.request;
import jakarta.validation.constraints.Min; import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.NotNull;
public record WarehouseBatchSizeRequestDTO(
    @NotBlank(message = "Size is required") String size,
    @NotNull(message = "Quantity is required") @Min(value = 0, message = "Quantity cannot be negative") Integer quantity
) {}
