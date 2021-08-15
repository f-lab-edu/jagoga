package com.project.jagoga.products.infrastructure;

import com.project.jagoga.products.domain.Product;
import com.project.jagoga.products.domain.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class MemoryProductRepository implements ProductRepository {

    private static ConcurrentMap<Long, Product> productStore = new ConcurrentHashMap<>();
    private static long sequence = 0l;

    @Override
    public Long save(Product product) {
        product.setProductId(++sequence);
        productStore.put(product.getProductId(), product);
        return product.getProductId();
    }

    @Override
    public Long delete(Long productId) {
        return productStore.remove(productId).getProductId();
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productStore.values());
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.ofNullable(productStore.get(productId));
    }

    @Override
    public Optional<Product> findByName(String productName) {
        return productStore.values().stream()
                .filter(product -> product.getProductName().equals(productName))
                .findAny();
    }
}
