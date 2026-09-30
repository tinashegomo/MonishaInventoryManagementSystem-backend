package com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.response;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.product.response.ProductResponseDTO;
import java.time.LocalDateTime; import java.util.List; import java.util.UUID;
public record WarehouseBatchResponseDTO(
    UUID batchId, String batchName, Integer batchPrice, Integer totalPrice,
    String type, String variant, String color, Integer totalQuantity,
    String description, String createdBy, LocalDateTime createdAt, LocalDateTime updatedAt,
    List<WarehouseBatchSizeResponseDTO> batchSizes, List<ProductResponseDTO> products, LocalDateTime depletedAt
) {}
