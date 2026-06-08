package com.tinasheGomo.MonishaInventoryManagementSystem.dto.customer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CustomerResponseDTO {

    private UUID customerId;

    private String customerName;

    private String phoneNumber;

    private String alternativePhoneNumber;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
