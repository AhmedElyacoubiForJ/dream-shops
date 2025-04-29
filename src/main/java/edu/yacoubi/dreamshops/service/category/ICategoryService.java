package edu.yacoubi.dreamshops.service.category;

import edu.yacoubi.dreamshops.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Category getCategoryByIdOrThrow(Long categoryId);

    Optional<Category> getCategoryByName(String name);

    List<Category> getAllCategories();

    Category addCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategoryById(Long categoryId);
}
