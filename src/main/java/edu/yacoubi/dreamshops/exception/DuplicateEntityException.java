package edu.yacoubi.dreamshops.exception;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String entityName, Long id) {
        super(entityName + " with ID " + id + " already exists.");
    }

    public DuplicateEntityException(String entityName, Long id, Throwable cause) {
        super(entityName + " with ID " + id + " already exists.", cause);
    }
}
