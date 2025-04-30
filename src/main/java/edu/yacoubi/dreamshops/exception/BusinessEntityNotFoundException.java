package edu.yacoubi.dreamshops.exception;

public class BusinessEntityNotFoundException extends RuntimeException {
    public BusinessEntityNotFoundException(String message) {
        super(message);
    }

    public BusinessEntityNotFoundException(String entityName, Long id) {
        super(entityName + " with ID " + id + " not found.");
    }

    public BusinessEntityNotFoundException(String entityName, Long id, Throwable cause) {
        super(entityName + " with ID " + id + " not found.", cause);
    }
}
