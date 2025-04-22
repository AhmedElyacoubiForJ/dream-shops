package edu.yacoubi.dreamshops.service.validation;

public interface BaseValidator<T, ID> {
    T getValidated(ID id);

    void throwIfNotExists(ID id);
}
