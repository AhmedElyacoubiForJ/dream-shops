package edu.yacoubi.dreamshops.service.validation;

public interface BaseValidator<T, ID> {
    T validateExists(ID id);
}
