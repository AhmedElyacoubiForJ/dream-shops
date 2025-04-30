package edu.yacoubi.dreamshops.mapping;

public interface EntityMapper<E, D> {
    E toEntity(D dto);

    D toDTO(E entity);
}

