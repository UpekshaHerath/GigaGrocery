package com.GigaGrocery.product_service.controller;

import com.GigaGrocery.product_service.dto.StockRequest;
import com.GigaGrocery.product_service.model.Stock;
import com.GigaGrocery.product_service.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    /**
     * Create a new stock for a product
     * @param productId
     * @param stockRequest
     */
    @PostMapping("/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStock(@PathVariable String productId, @RequestBody StockRequest stockRequest) {
        stockService.createStock(productId, stockRequest);
    }

    /**
     * Update the stock of a product
     * @param productId
     * @param stockId
     * @param stockRequest
     */
    @PutMapping("/{productId}/{stockId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStock(@PathVariable String productId, @PathVariable String stockId, @RequestBody StockRequest stockRequest) {
        log.info("Stock id is {}", stockId);
        stockService.updateStock(productId, stockId, stockRequest);
    }

    /**
     * Get all the stocks of a product
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Stock> getStock(@PathVariable String productId) {
        return stockService.getStock(productId);
    }

    /**
     * Get a specific stock of a product
     * @param productId
     * @param stockId
     */
    @GetMapping("/{productId}/{stockId}")
    @ResponseStatus(HttpStatus.OK)
    public Stock getStock(@PathVariable String productId, @PathVariable String stockId) {
        return stockService.getStock(productId, stockId);
    }

    /**
     * Restore an order of a product
     * Delete a specific stock of a product
     * priority goes to old stocks
     * @param productId
     * @param stockId
     */
    @DeleteMapping("/{productId}/{stockId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStock(@PathVariable String productId, @PathVariable String stockId) {
        stockService.deleteStock(productId, stockId);
    }

    @DeleteMapping("reduce/{productId}/{itemCount}")
    @ResponseStatus(HttpStatus.OK)
    public void reduceStock(@PathVariable String productId, @PathVariable int itemCount) {
        stockService.reduceStock(productId, itemCount);
    }

}
