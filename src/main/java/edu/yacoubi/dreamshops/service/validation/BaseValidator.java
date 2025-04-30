package edu.yacoubi.dreamshops.service.validation;

public interface BaseValidator<T, ID> {
    T getValidatedOrThrow(ID id);

    void existsByIdOrThrow(ID id);

    //void validateBeforeDelete(ID id);

    //void validateBeforeUpdate(T entity);
}
