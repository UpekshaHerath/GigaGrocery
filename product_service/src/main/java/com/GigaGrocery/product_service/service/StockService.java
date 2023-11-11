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
                List<Stock> stocks = product.get().getStocks();
                Stock stock = Stock.builder()
                        .date(LocalDateTime.now())
                        .quantity(stockRequest.getQuantity())
                        .price(stockRequest.getPrice())
                        .build();
                stocks.add(stock);
                product.get().setStocks(stocks);
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
                List<Stock> stocks = product.get().getStocks();
                log.info(stocks.toString());
                if (stocks.size() == 0) {
                    throw new RuntimeException("Stock does not exist");
                } else {
                    if (stocks.size() <= Integer.parseInt(stockId)) {
                        throw new RuntimeException("Stock does not exist");
                    }
                }
                Stock stock = Stock.builder()
                        .date(stocks.get(Integer.parseInt(stockId)).getDate())
                        .quantity(stockRequest.getQuantity())
                        .price(stockRequest.getPrice())
                        .build();
                stocks.set(Integer.parseInt(stockId), stock);
                product.get().setStocks(stocks);
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
                List<Stock> stocks = product.get().getStocks();
                log.info(stocks.toString());
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
                List<Stock> stocks = product.get().getStocks();
                if (stocks.size() == 0) {
                    throw new RuntimeException("Stock does not exist");
                } else {
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
                List<Stock> stocks = product.get().getStocks();
                if (stocks.size() == 0) {
                    throw new RuntimeException("Stock does not exist");
                } else {
                    if (stocks.size() <= Integer.parseInt(stockId)) {
                        throw new RuntimeException("Invalid Stock ID");
                    }
                }
                stocks.remove(Integer.parseInt(stockId));
                product.get().setStocks(stocks);
                productRepository.save(product.get());
                log.info("Stock {} of product {} deleted", stockId, productId);
            } else {
                throw new RuntimeException("Product does not exist");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void reduceStock(String productId, int itemCount) {
        try {
            if (productRepository.existsById(productId)) {
                Optional<Product> product = productRepository.findById(productId);
                List<Stock> stocks = product.get().getStocks();
                if (stocks.size() == 0) {
                    throw new RuntimeException("No products to issue");
                } else {
                    log.info("Stocks are present");
                    int numberOfItemsNeed = itemCount;
                    if (getTotalNumberOfItemsInStocks(productId) < itemCount) {
                        throw new RuntimeException("Not enough items in the stocks");
                    }
                    List<Stock> sortedStocksOnDate = stocks;
                    sortedStocksOnDate.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
                    for (Stock stock : sortedStocksOnDate) {
                        if (stock.getQuantity() >= numberOfItemsNeed) {
                            stock.setQuantity(stock.getQuantity() - numberOfItemsNeed);
                            numberOfItemsNeed = 0;
                            break;
                        } else {
                            numberOfItemsNeed -= stock.getQuantity();
                            stock.setQuantity(0);
                        }
                    }
                }
                product.get().setStocks(stocks);
                productRepository.save(product.get());
                log.info("Stocks are reduced. Process completed successfully");
            } else {
                throw new RuntimeException("Product does not exist");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private int getTotalNumberOfItemsInStocks(String productId) {
        try {
            if (productRepository.existsById(productId)) {
                Optional<Product> product = productRepository.findById(productId);
                List<Stock> stocks = product.get().getStocks();
                int totalNumberOfItemsInStock = 0;
                for (Stock stock : stocks) {
                    totalNumberOfItemsInStock += stock.getQuantity();
                }
                return totalNumberOfItemsInStock;
            }
            return 0;
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
    }
}
