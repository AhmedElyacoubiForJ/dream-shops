package edu.yacoubi.dreamshops.service.category;

import edu.yacoubi.dreamshops.model.Category;

import java.util.Optional;

public interface ICategoryService {
    Category getCategoryByIdOrThrow(Long categoryId);

    Category addCategory(Category category);

    Optional<Category> getCategoryByName(String name);
}
