package com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
public record WarehouseBatchRequestDTO(
    @NotBlank(message = "Batch name is required") String batchName,
    @NotNull(message = "Batch price is required") Integer batchPrice,
    @NotBlank(message = "Type is required") String type,
    @NotBlank(message = "Variant is required") String variant,
    @NotBlank(message = "Color is required") String color,
    String description,
    @NotEmpty(message = "Batch must contain at least one size") List<WarehouseBatchSizeRequestDTO> batchSizes
) {}
