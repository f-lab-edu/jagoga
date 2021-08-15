package com.project.jagoga.products.presentation.dto;

import com.project.jagoga.products.domain.Address;
import com.project.jagoga.products.domain.Product;
import com.project.jagoga.products.domain.ProductType;
import com.project.jagoga.products.domain.grade.Grade;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRequestDto {

    private String productName;
    private String phoneNumber;
    private Address address;
    private ProductType productType;
    private Grade grade;
    private String description;
    private String information;

    private ProductRequestDto() {
    }

    public ProductRequestDto(String productName, String phoneNumber,
                             Address address, ProductType productType,
                             Grade grade, String description, String information) {
        this.productName = productName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.productType = productType;
        this.grade = grade;
        this.description = description;
        this.information = information;
    }

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .phoneNumber(phoneNumber)
                .address(address)
                .productType(productType)
                .grade(grade)
                .description(description)
                .information(information)
                .build();
    }
}
