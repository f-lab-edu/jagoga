package com.project.jagoga.products.application;

import com.project.jagoga.exception.products.DuplicatedProductException;
import com.project.jagoga.exception.products.NotExistProductException;
import com.project.jagoga.products.domain.Product;
import com.project.jagoga.products.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Long saveProduct(Product product) {
        validateDuplicatedProduct(product);
        return productRepository.save(product);
    }

    public Long deleteProduct(Long productId) {
        productRepository.findById(productId)
                .ifPresentOrElse(
                        p -> productRepository.delete(productId),
                        () -> {
                            throw new NotExistProductException();
                        });
        return productId;
    }

    public List<Product> getProductAllList() {
        return productRepository.findAll();
    }

    private void validateDuplicatedProduct(Product product) {
        productRepository.findByName(product.getProductName())
                .ifPresent(p -> {
                    throw new DuplicatedProductException();
                });
    }
}
