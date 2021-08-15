package com.project.jagoga.products.domain;

import com.project.jagoga.products.domain.grade.Grade;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Product {

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

    protected Product() {
    }

    public Product(Long productId, String productName, String phoneNumber,
                   Address address, ProductType productType, Grade grade,
                   String description, String information, int lowPrice,
                   LocalDateTime createdAt, LocalDateTime updateAt) {
        this.productId = productId;
        this.productName = productName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.productType = productType;
        this.grade = grade;
        this.description = description;
        this.information = information;
        this.lowPrice = lowPrice;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
