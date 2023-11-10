package com.GigaGrocery.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Stock {
    @Id
    private String stockID;
    private String date;
    private int quantity;
    private BigDecimal price;
}
