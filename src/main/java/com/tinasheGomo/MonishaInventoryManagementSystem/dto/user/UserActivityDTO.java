package com.tinasheGomo.MonishaInventoryManagementSystem.dto.user;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.order.response.OrderResponseDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.product.response.ProductResponseDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.warehouse.response.WarehouseBatchResponseDTO;
import java.util.List;
public record UserActivityDTO(
    UserResponseDTO user, long totalOrdersCreated, long totalProductsCreated, long totalBatchesCreated,
    List<OrderResponseDTO> recentOrders, List<ProductResponseDTO> recentProducts, List<WarehouseBatchResponseDTO> recentBatches
) {}
