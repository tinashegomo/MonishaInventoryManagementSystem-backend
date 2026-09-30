package com.tinasheGomo.MonishaInventoryManagementSystem.dto.product.response;
import java.time.LocalDateTime; import java.util.UUID;
public record ProductDepletedHistoryResponseDTO(UUID depletedId, UUID productId, LocalDateTime depletedAt) {}
