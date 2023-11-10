package com.GigaGrocery.product_service.repository;

import com.GigaGrocery.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/***
 * Provide interfaces for database interaction using relevant dependencies like Spring data JPA
 */

public interface ProductRepository extends MongoRepository<Product, String> {

    boolean existsByName(String name);
}
