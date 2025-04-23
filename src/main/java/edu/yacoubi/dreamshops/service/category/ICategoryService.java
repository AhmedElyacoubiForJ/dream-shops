package edu.yacoubi.dreamshops.service.category;

import edu.yacoubi.dreamshops.model.Category;

import java.util.Optional;

public interface ICategoryService {
    Category getCategoryById(Long categoryId);

    boolean existsByName(String name);

    Category createCategory(Category category);

    Optional<Category> getCategoryByName(String name);
}
