package com.tinasheGomo.MonishaInventoryManagementSystem.controller.publicApi;

import com.tinasheGomo.MonishaInventoryManagementSystem.dto.customer.CustomerRequestDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.dto.customer.CustomerResponseDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.entity.customer.CustomerEntity;
import com.tinasheGomo.MonishaInventoryManagementSystem.mapper.customer.CustomerMapper;
import com.tinasheGomo.MonishaInventoryManagementSystem.repository.customer.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/public/imsClient/customers")
@RequiredArgsConstructor
public class PublicCustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    /**
     * Find or create a customer by phone number.
     * Idempotent — if a customer with this phone already exists, return it.
     * This prevents duplicate customer records when the ecom backend
     * calls this endpoint multiple times for the same customer.
     */
    @PostMapping
    public CustomerResponseDTO findOrCreateCustomer(@RequestBody @Valid CustomerRequestDTO requestDTO) {

        // Check if a customer with this phone number already exists
        Optional<CustomerEntity> existing = customerRepository.findByPhoneNumber(requestDTO.getPhoneNumber());

        if (existing.isPresent()) {
            return customerMapper.toResponse(existing.get());
        }

        // Create new customer
        CustomerEntity customer = customerMapper.toEntity(requestDTO);
        CustomerEntity savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }
}
