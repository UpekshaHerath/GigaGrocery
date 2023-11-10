package com.GigaGrocery.product_service.service;

import com.GigaGrocery.product_service.dto.ProductRequest;
import com.GigaGrocery.product_service.dto.ProductResponse;
import com.GigaGrocery.product_service.model.Product;
import com.GigaGrocery.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * Provide business logic and act as a intermediary layer in controller and repository
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(String.valueOf(Integer.parseInt(id)));
        log.info("Product with id {} is deleted", id);
    }

    public void updateProduct(String id, ProductRequest productRequest) {
        try {
            Product productInDb = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

            Product product = Product.builder()
                    .id(productInDb.getId())
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .build();

            productRepository.save(product);
            log.info("Product {} is updated", product.getId());
        } catch (Exception e) {
            log.error("Product with id {} is not found", id);
        }

    }
}
