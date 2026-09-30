package com.tinasheGomo.MonishaInventoryManagementSystem.dto.product.request;
import jakarta.validation.constraints.Min; import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.NotEmpty; import jakarta.validation.constraints.NotNull;
import java.util.List; import java.util.UUID;
public record ProductRequestDTO(
    @NotBlank(message = "Product name is required") String productName,
    String description, @NotNull(message = "Product price is required") Integer productPrice,
    @NotEmpty(message = "Product must contain at least one size") List<ProductSizeRequestDTO> productSizes,
    UUID schoolId,
    @NotNull(message = "Batch ID is required") UUID batchId
) {}
