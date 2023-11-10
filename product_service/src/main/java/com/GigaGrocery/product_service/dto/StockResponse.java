package com.GigaGrocery.product_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {
    private LocalDateTime date;
    private int quantity;
    private BigDecimal price;
}
