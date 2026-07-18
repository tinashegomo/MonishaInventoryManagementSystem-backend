package com.tinasheGomo.MonishaInventoryManagementSystem.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServiceApiKeyConfig {

    @Value("${service.api-keys.ecom-catalog}")
    private String ecomCatalogKey;

    @Value("${service.api-keys.ecom-customers}")
    private String ecomCustomersKey;

    @Value("${service.api-keys.ecom-orders}")
    private String ecomOrdersKey;
}
