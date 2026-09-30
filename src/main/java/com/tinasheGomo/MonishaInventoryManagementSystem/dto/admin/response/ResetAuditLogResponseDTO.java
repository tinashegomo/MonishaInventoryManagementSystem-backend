package com.tinasheGomo.MonishaInventoryManagementSystem.dto.admin.response;
import java.time.LocalDateTime; import java.util.UUID;
public record ResetAuditLogResponseDTO(UUID logId, String performedBy, String tablesCleared, String rowCounts, LocalDateTime resetAt) {}
