package com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class WarehouseBatchResponseDTO {

    private UUID batchId;

    private String batchName;

    private Integer batchPrice;

    private Integer totalPrice;

    private String type;

    private String variant;

    private String color;

    private Integer totalQuantity;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<WarehouseBatchSizeResponseDTO> batchSizes;
}
