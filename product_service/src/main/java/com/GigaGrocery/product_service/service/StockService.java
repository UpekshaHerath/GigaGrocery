package com.GigaGrocery.product_service.service;

import com.GigaGrocery.product_service.dto.StockRequest;
import com.GigaGrocery.product_service.model.Product;
import com.GigaGrocery.product_service.model.Stock;
import com.GigaGrocery.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
    private final ProductRepository productRepository;

    public void createStock(String productID, StockRequest stockRequest) {
        try {
            if (productRepository.existsById(productID)) {
                Optional<Product> product = productRepository.findById(productID);
                // get the list of stocks from the product
                List<Stock> stocks = product.get().getStocks();
                // then we can create a new stock
                Stock stock = Stock.builder()
                        .date(LocalDateTime.now())
                        .quantity(stockRequest.getQuantity())
                        .price(stockRequest.getPrice())
                        .build();
                // add the new stock to the list
                stocks.add(stock);
                // then we can update the product
                product.get().setStocks(stocks);
                // then we can save the product
                productRepository.save(product.get());
                log.info("Stock is saved");
            } else {
                throw new RuntimeException("Product does not exist");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    public void updateStock(String productId, String stockId, StockRequest stockRequest) {
        try {
            if (productRepository.existsById(productId)) {
                Optional<Product> product = productRepository.findById(productId);
                // get the list of stocks from the product
                List<Stock> stocks = product.get().getStocks();
                log.info(stocks.toString());
                // check that stock is present or not
                if (stocks.size() == 0) {
                    throw new RuntimeException("Stock does not exist");
                } else {
                    // check that stock is present or not
                    if (stocks.size() <= Integer.parseInt(stockId)) {
                        throw new RuntimeException("Stock does not exist");
                    }
                }
                // then we can create a new stock depending on the given values
                Stock stock = Stock.builder()
                        .date(stocks.get(Integer.parseInt(stockId)).getDate())
                        .quantity(stockRequest.getQuantity())
                        .price(stockRequest.getPrice())
                        .build();
                // replace that stock with the new stock
                stocks.set(Integer.parseInt(stockId), stock);
                // then we can update the product
                product.get().setStocks(stocks);
                // then we can save the product
                productRepository.save(product.get());
                log.info("Stock {} of product {} updated", stockId, productId);
            } else {
                throw new RuntimeException("Product does not exist");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public List<Stock> getStock(String productId) {
        log.info("HI HI HI");
        try {
            if (productRepository.existsById(productId)) {
                Optional<Product> product = productRepository.findById(productId);
                // get the list of stocks from the product
                List<Stock> stocks = product.get().getStocks();

                log.info(stocks.toString());

                // check that stock is present or not
                if (stocks.size() == 0) {
                    log.info("this");
                    throw new RuntimeException("There are no any stocks in this product");
                }
                log.info("name name name");
                return stocks;
            } else {
                throw new RuntimeException("Product does not exist");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Stock getStock(String productId, String stockId) {
        try {
            if (productRepository.existsById(productId)) {
                Optional<Product> product = productRepository.findById(productId);
                // get the list of stocks from the product
                List<Stock> stocks = product.get().getStocks();
                // check that stock is present or not
                if (stocks.size() == 0) {
                    throw new RuntimeException("Stock does not exist");
                } else {
                    // check that stock is present or not
                    if (stocks.size() <= Integer.parseInt(stockId)) {
                        throw new RuntimeException("Invalid Stock ID");
                    }
                }
                return stocks.get(Integer.parseInt(stockId));
            } else {
                throw new RuntimeException("Product does not exist");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void deleteStock(String productId, String stockId) {
        try {
            if (productRepository.existsById(productId)) {
                Optional<Product> product = productRepository.findById(productId);
                // get the list of stocks from the product
                List<Stock> stocks = product.get().getStocks();
                // check that stock is present or not
                if (stocks.size() == 0) {
                    throw new RuntimeException("Stock does not exist");
                } else {
                    // check that stock is present or not
                    if (stocks.size() <= Integer.parseInt(stockId)) {
                        throw new RuntimeException("Invalid Stock ID");
                    }
                }
                // remove the stock from the list
                stocks.remove(Integer.parseInt(stockId));
                // then we can update the product
                product.get().setStocks(stocks);
                // then we can save the product
                productRepository.save(product.get());
                log.info("Stock {} of product {} deleted", stockId, productId);
            } else {
                throw new RuntimeException("Product does not exist");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
