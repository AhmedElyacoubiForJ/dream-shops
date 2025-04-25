package edu.yacoubi.dreamshops.service.validation;

public interface BaseValidator<T, ID> {
    T getValidatedOrThrow(ID id);

    void checkExistsOrThrow(ID id);
}
