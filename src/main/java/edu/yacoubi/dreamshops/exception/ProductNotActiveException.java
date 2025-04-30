package edu.yacoubi.dreamshops.exception;

public class ProductNotActiveException extends RuntimeException {
    public ProductNotActiveException(String message) {
        super(message);
    }

    public ProductNotActiveException(Long productId) {
        super("Product with ID " + productId + " is not active.");
    }

    public ProductNotActiveException(Long productId, Throwable cause) {
        super("Product with ID " + productId + " is not active.", cause);
    }
}
