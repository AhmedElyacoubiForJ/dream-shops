package edu.yacoubi.dreamshops.mapping;

public interface UpdatableEntity<E, D> {
    void updateEntity(E entity, D dto);
}
