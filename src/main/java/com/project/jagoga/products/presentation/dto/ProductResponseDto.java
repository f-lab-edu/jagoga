package com.project.jagoga.products.presentation.dto;

import com.project.jagoga.products.domain.Address;
import com.project.jagoga.products.domain.Product;
import com.project.jagoga.products.domain.ProductType;
import com.project.jagoga.products.domain.grade.Grade;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static java.util.stream.Collectors.*;

@Getter
@Builder
public class ProductResponseDto {

    private String productName;
    private Address address;
    private ProductType productType;
    private Grade grade;
    private int lowPrice;

    private ProductResponseDto() {
    }

    public ProductResponseDto(String productName, Address address,
                              ProductType productType, Grade grade, int lowPrice) {
        this.productName = productName;
        this.address = address;
        this.productType = productType;
        this.grade = grade;
        this.lowPrice = lowPrice;
    }

    public static ProductResponseDto of(Product product) {
        return ProductResponseDto.builder()
                .productName(product.getProductName())
                .address(product.getAddress())
                .productType(product.getProductType())
                .grade(product.getGrade())
                .lowPrice(product.getLowPrice())
                .build();
    }

    public static List<ProductResponseDto> listOf(List<Product> products) {
        return products.stream()
                .map(product -> ProductResponseDto.of(product))
                .collect(toList());
    }
}
