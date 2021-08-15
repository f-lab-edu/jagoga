package com.project.jagoga.common.factory;

import com.project.jagoga.products.domain.Address;
import com.project.jagoga.products.domain.Product;
import com.project.jagoga.products.domain.ProductType;
import com.project.jagoga.products.domain.grade.Grade;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class MockProduct {

    private Long productId;
    private String productName;
    private String phoneNumber;
    private Address address;
    private ProductType productType;
    private Grade grade;
    private String description;
    private String information;
    private int lowPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public Product build() {
        return new Product(
                productId,
                productName,
                phoneNumber,
                address,
                productType,
                grade,
                description,
                information,
                lowPrice,
                createdAt,
                updateAt
        );
    }
}
