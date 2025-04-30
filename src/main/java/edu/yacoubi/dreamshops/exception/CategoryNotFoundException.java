package edu.yacoubi.dreamshops.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(Long id) {
        super("Category with ID " + id + " not found.");
    }
}
