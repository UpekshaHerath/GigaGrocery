package com.GigaGrocery.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/***
 * Reason for using two different classes for product request and response is that the attributes
 * are different in these two classes.
 *
 * Instead of ProductResponse class we can use Product class also, but as a good practice we
 * implement separate two classes for Product Request and Response.
 */

@Data // use to create getters and setters
@Builder // to craete the builder method
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
}
