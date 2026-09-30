package com.tinasheGomo.MonishaInventoryManagementSystem.dto.product.request;
import jakarta.validation.constraints.Min; import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.NotNull;
public record ProductSizeRequestDTO(
    @NotBlank(message = "Size is required") String size,
    @NotNull(message = "Quantity is required") @Min(value = 1, message = "Quantity must be greater than 0") Integer quantity
) {}
