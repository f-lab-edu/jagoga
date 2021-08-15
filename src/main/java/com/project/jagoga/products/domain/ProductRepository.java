package com.project.jagoga.products.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Long save(Product product);

    Long delete(Long productId);

    List<Product> findAll();

    Optional<Product> findById(Long productId);

    Optional<Product> findByName(String productName);
}
