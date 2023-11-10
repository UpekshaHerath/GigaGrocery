package com.GigaGrocery.product_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {
    private String stockID;
    private String date;
    private int quantity;
    private BigDecimal price;
}
