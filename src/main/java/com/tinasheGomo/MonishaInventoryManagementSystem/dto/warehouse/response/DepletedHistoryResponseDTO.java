package com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.response;
import java.time.LocalDateTime; import java.util.UUID;
public record DepletedHistoryResponseDTO(UUID depletedId, UUID batchId, LocalDateTime depletedAt) {}
