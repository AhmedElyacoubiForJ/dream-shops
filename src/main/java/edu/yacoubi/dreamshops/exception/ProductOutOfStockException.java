package edu.yacoubi.dreamshops.exception;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(String message) {
        super(message);
    }

    public ProductOutOfStockException(Long productId) {
        super("Product with ID " + productId + " is out of stock.");
    }

    public ProductOutOfStockException(Long productId, Throwable cause) {
        super("Product with ID " + productId + " is out of stock.", cause);
    }
}
