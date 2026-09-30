package com.tinasheGomo.MonishaInventoryManagementSystem.dto.measurement;
import java.math.BigDecimal; import java.util.UUID;
public record MeasurementResponseDTO(UUID measurementId, String measurementName, BigDecimal measurementValue, UUID orderItemId) {}
