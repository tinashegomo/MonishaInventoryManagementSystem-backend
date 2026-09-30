package com.tinasheGomo.MonishaInventoryManagementSystem.dto.product.response;
import java.time.LocalDateTime; import java.util.List; import java.util.UUID;
public record ProductResponseDTO(
    UUID productId, String productName, Integer productPrice, Integer totalPrice,
    UUID schoolId, String schoolName, UUID batchId, String batchName,
    String type, String variant, String color, Integer totalQuantity,
    String description, String createdBy, LocalDateTime createdAt, LocalDateTime updatedAt,
    List<ProductSizeResponseDTO> productSizes, LocalDateTime depletedAt
) {}
