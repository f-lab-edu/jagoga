package com.project.jagoga.products.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.jagoga.common.factory.ProductFactory;
import com.project.jagoga.exception.products.DuplicatedProductException;
import com.project.jagoga.products.domain.Product;
import com.project.jagoga.products.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private static final long PRODUCT_ID = 1L;

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("정상적으로 상품 등록 시 id가 생성된다.")
    @Test
    void saveProduct_Success() {
        // given
        Product product = ProductFactory.product();
        when(productRepository.save(any())).thenReturn(PRODUCT_ID);

        // when
        Long productId = productService.saveProduct(product);

        // then
        assertThat(productId).isEqualTo(PRODUCT_ID);
    }

    @DisplayName("중복된 상품명 상품 등록 시 예외를 반환한다.")
    @Test
    void saveProduct_Exception() {
        // given
        Product product = ProductFactory.product();
        when(productRepository.findByName(product.getProductName())).thenReturn(Optional.of(product));

        // then
        assertThrows(DuplicatedProductException.class, () -> productService.saveProduct(product));
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void delete_Success() {
        // given
        Product product = ProductFactory.createProduct(PRODUCT_ID, "testProduct");
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // when
        productService.deleteProduct(PRODUCT_ID);

        // then
        verify(productRepository, times(1))
                .delete(PRODUCT_ID);
    }
}