package com.tinasheGomo.MonishaInventoryManagementSystem.service.warehouse;

import com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.request.WarehouseBatchRequestDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.request.WarehouseBatchSizeRequestDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.response.WarehouseBatchResponseDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.entity.warehouse.WarehouseBatchEntity;
import com.tinasheGomo.MonishaInventoryManagementSystem.entity.warehouse.WarehouseBatchSizeEntity;
import com.tinasheGomo.MonishaInventoryManagementSystem.exception.exceptions.DuplicateException;
import com.tinasheGomo.MonishaInventoryManagementSystem.exception.exceptions.NotFoundException;
import com.tinasheGomo.MonishaInventoryManagementSystem.mapper.warehouse.WarehouseBatchMapper;
import com.tinasheGomo.MonishaInventoryManagementSystem.repository.warehouse.WarehouseBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseBatchService {

    private final WarehouseBatchRepository batchRepository;
    private final WarehouseBatchMapper batchMapper;
    private final WarehouseBatchSizeService batchSizeService;

    /**
     * CREATE BATCH
     */
    public WarehouseBatchResponseDTO createWarehouseBatch(WarehouseBatchRequestDTO requestDTO) {

        // 1. Prevent duplicates
        if (batchRepository.existsByBatchName(requestDTO.getBatchName())) {
            throw new DuplicateException("Batch already exists");
        }

        // 2. Map basic fields (NO sizes yet properly linked)
        WarehouseBatchEntity batch = batchMapper.toEntity(requestDTO);

        // 3. Save batch first (important for FK relationship safety)
        WarehouseBatchEntity savedBatch = batchRepository.save(batch);

        // 4. Add sizes using CHILD SERVICE
        for (WarehouseBatchSizeRequestDTO sizeDTO : requestDTO.getBatchSizes()) {
            batchSizeService.addSizeToBatch(savedBatch.getBatchId(), sizeDTO);
        }

        // 5. Reload batch with sizes (important for accurate calculations)
        WarehouseBatchEntity updatedBatch = batchRepository.findByBatchId(savedBatch.getBatchId())
                .orElseThrow(() -> new NotFoundException("Batch not found after save"));

        // 6. Calculate totals using SERVICE
        int totalQuantity = batchSizeService.calculateBatchTotalQuantity(updatedBatch);
        updatedBatch.setTotalQuantity(totalQuantity);

        updatedBatch.setTotalPrice(totalQuantity * updatedBatch.getBatchPrice());

        // 7. Save final updated batch
        WarehouseBatchEntity finalBatch = batchRepository.save(updatedBatch);

        return batchMapper.toResponse(finalBatch);
    }

    public WarehouseBatchResponseDTO getWarehouseBatchById(UUID batchId) {

        WarehouseBatchEntity batch = batchRepository.findByBatchId(batchId)
                .orElseThrow(() -> new NotFoundException("Batch not found"));

        return batchMapper.toResponse(batch);
    }

    public List<WarehouseBatchResponseDTO> getAllWarehouseBatches() {

        return batchMapper.toResponseList(batchRepository.findAll());
    }

    public void deleteWarehouseBatch(UUID batchId) {

        WarehouseBatchEntity batch = batchRepository.findByBatchId(batchId)
                .orElseThrow(() -> new NotFoundException("Batch not found"));

        batchRepository.delete(batch);
    }
}
