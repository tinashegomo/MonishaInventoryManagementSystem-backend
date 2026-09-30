package com.tinasheGomo.MonishaInventoryManagementSystem.dto.shared;
import jakarta.validation.constraints.Min; import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.NotNull;
public record SizeQuantityDTO(
    @NotBlank(message = "Size is required") String size,
    @NotNull(message = "Quantity is required") @Min(value = 1, message = "Quantity must be at least 1") Integer quantity
) {}
