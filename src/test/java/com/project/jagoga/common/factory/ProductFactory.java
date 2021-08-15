package com.project.jagoga.common.factory;

import com.project.jagoga.products.domain.Product;
import com.project.jagoga.products.presentation.dto.ProductRequestDto;
import lombok.Builder;

@Builder
public class ProductFactory {

    private ProductFactory() {
    }

    public static Product product(Long productId, String productName) {
        return createProduct(productId, productName);
    }

    public static Product product() {
        return createProduct(null, "testProduct");
    }

    public static Product createProduct(Long productId, String productName) {
        return MockProduct.builder()
                .productId(productId)
                .productName(productName)
                .build().build();
    }
}
