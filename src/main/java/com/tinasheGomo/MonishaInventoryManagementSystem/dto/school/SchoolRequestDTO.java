package com.tinasheGomo.MonishaInventoryManagementSystem.dto.school;
import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.Size;
public record SchoolRequestDTO(
    @NotBlank(message = "School name is required") @Size(min = 2, max = 100, message = "School name must be between 2 and 100 characters") String schoolName
) {}
