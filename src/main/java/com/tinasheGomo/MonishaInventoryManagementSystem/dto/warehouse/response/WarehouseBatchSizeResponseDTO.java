package com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.response;
import java.time.LocalDateTime; import java.util.UUID;
public record WarehouseBatchSizeResponseDTO(UUID sizeId, String size, Integer quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {}
