package com.project.jagoga.products.presentation;

import com.project.jagoga.products.application.ProductService;
import com.project.jagoga.products.domain.Product;
import com.project.jagoga.products.presentation.dto.ProductRequestDto;
import com.project.jagoga.products.presentation.dto.ProductResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.project.jagoga.products.presentation.ProductController.*;

@RestController
@RequestMapping(PRODUCT_API_URI)
public class ProductController {
    public static final String PRODUCT_API_URI = "/api/product";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody final ProductRequestDto productRequestDto) {
        Long productId = productService.saveProduct(productRequestDto.toEntity());
        return ResponseEntity
                .created(URI.create(PRODUCT_API_URI + "/" + productId))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProductAllList() {
        List<ProductResponseDto> productAllList = ProductResponseDto.listOf(productService.getProductAllList());
        return ResponseEntity.ok(productAllList);
    }
}
