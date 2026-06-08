package com.tinasheGomo.MonishaInventoryManagementSystem.controller.warehouse;

import com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.request.WarehouseBatchRequestDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.response.WarehouseBatchResponseDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.service.warehouse.WarehouseBatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/monishaInventory/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseBatchService warehouseBatchService;

    @PostMapping("/create-batch")
    public WarehouseBatchResponseDTO createWarehouseBatch(@RequestBody @Valid WarehouseBatchRequestDTO requestDTO){
        return warehouseBatchService.createWarehouseBatch(requestDTO);
    }

    @GetMapping("/get-batch-byId/{batchId}")
    public WarehouseBatchResponseDTO getWarehouseBatchById(@PathVariable UUID batchId){
        return warehouseBatchService.getWarehouseBatchById(batchId);
    }

    @GetMapping("/get-all-batches")
    public List<WarehouseBatchResponseDTO> getAllWarehouseBatches(){
        return warehouseBatchService.getAllWarehouseBatches();
    }

    @DeleteMapping("/delete-batch/{batchId}")
    public void deleteWarehouseBatch(@PathVariable UUID batchId){
        warehouseBatchService.deleteWarehouseBatch(batchId);
    }
}
