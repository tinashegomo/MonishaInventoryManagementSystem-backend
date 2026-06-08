package com.tinasheGomo.MonishaInventoryManagementSystem.service.product;

import com.tinasheGomo.MonishaInventoryManagementSystem.dto.product.request.ProductRequestDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.product.request.ProductSizeRequestDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.product.response.ProductResponseDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.entity.product.ProductEntity;
import com.tinasheGomo.MonishaInventoryManagementSystem.entity.school.SchoolEntity;
import com.tinasheGomo.MonishaInventoryManagementSystem.entity.warehouse.WarehouseBatchEntity;
import com.tinasheGomo.MonishaInventoryManagementSystem.exception.exceptions.NotFoundException;
import com.tinasheGomo.MonishaInventoryManagementSystem.mapper.product.ProductMapper;
import com.tinasheGomo.MonishaInventoryManagementSystem.repository.product.ProductRepository;
import com.tinasheGomo.MonishaInventoryManagementSystem.repository.school.SchoolRepository;
import com.tinasheGomo.MonishaInventoryManagementSystem.repository.warehouse.WarehouseBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final SchoolRepository schoolRepository;
    private final WarehouseBatchRepository batchRepository;

    private final ProductSizeService productSizeService;

    /**
     * CREATE PRODUCT
     */
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        // 1. Map basic product fields
        ProductEntity product = productMapper.toEntity(requestDTO);

        // 2. Attach school (optional)
        if (requestDTO.getSchoolId() != null) {
            SchoolEntity school = schoolRepository.findBySchoolId(requestDTO.getSchoolId())
                    .orElseThrow(() -> new NotFoundException("School not found"));

            product.setSchool(school);
        }

        // 3. Attach batch (mandatory)
        WarehouseBatchEntity batch = batchRepository.findByBatchId(requestDTO.getBatchId())
                .orElseThrow(() -> new NotFoundException("Batch not found"));

        product.setBatch(batch);

        // 4. Override product attributes from batch (business rule)
        product.setType(batch.getType());
        product.setVariant(batch.getVariant());
        product.setColor(batch.getColor());

        // 5. Save product first
        ProductEntity savedProduct = productRepository.save(product);

        // 6. Add sizes via child service
        for (ProductSizeRequestDTO sizeDTO : requestDTO.getProductSizes()) {
            productSizeService.addSizeToProduct(savedProduct.getProductId(), sizeDTO);
        }

        // 7. Reload product (important for correct totals)
        ProductEntity updatedProduct = productRepository.findByProductId(savedProduct.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found after save"));

        // 8. Calculate total quantity
        int totalQuantity = productSizeService.calculateProductTotalQuantity(updatedProduct);
        updatedProduct.setTotalQuantity(totalQuantity);

        // 9. Calculate total price
        updatedProduct.setTotalPrice(totalQuantity * updatedProduct.getProductPrice());

        // 10. Save final product
        ProductEntity finalProduct = productRepository.save(updatedProduct);

        return productMapper.toResponse(finalProduct);
    }

    public ProductResponseDTO getProductById(UUID productId) {

        ProductEntity product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return productMapper.toResponse(product);
    }

    public List<ProductResponseDTO> getAllProducts() {

        return productMapper.toResponseList(productRepository.findAll());
    }

    public void deleteProduct(UUID productId) {

        ProductEntity product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        productRepository.delete(product);
    }
}
