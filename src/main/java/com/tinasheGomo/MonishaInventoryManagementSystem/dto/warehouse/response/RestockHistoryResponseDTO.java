package com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.response;
import java.time.LocalDateTime; import java.util.UUID;
public record RestockHistoryResponseDTO(UUID restockId, UUID batchId, String size, Integer quantityAdded, String restockedBy, LocalDateTime restockedAt) {}
