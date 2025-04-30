package edu.yacoubi.dreamshops.service.category;

import edu.yacoubi.dreamshops.exception.BusinessEntityNotFoundException;
import edu.yacoubi.dreamshops.exception.DuplicateEntityException;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.repository.CategoryRepository;
import edu.yacoubi.dreamshops.service.validation.CategoryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;

    @Override
    public Category getCategoryByIdOrThrow(final Long categoryId) {
        log.info("::getCategoryById started for categoryId {}", categoryId);

        try {
            Category foundCategory = categoryValidator.getValidatedOrThrow(categoryId);
            log.info("::getCategoryById completed successfully for categoryId {}", categoryId);

            return foundCategory;
        } catch (Exception e) {
            log.error("::getCategoryById error for categoryId {}: {}", categoryId, e.getMessage());
            throw e;
        }
    }

    @Override
    public Category addCategory(final Category category) {
        log.info("::addCategory started for category {}", category);

        if (category == null || category.getName() == null) {
            log.error("::addCategory error: Category name must not be null");
            throw new IllegalArgumentException("Category name must not be null");
        }

        String categoryName = category.getName();
        if (categoryValidator.existsByName(categoryName)) {
            log.error("::addCategory error: Entity already exists with name: {}", categoryName);
            throw new DuplicateEntityException("Entity already exists with name: " + categoryName);
        }

        Category savedCategory = categoryRepository.save(category);
        log.info("::addCategory completed successfully for category {}", savedCategory);

        return savedCategory;
    }

    @Override
    public Category updateCategory(final Category category) {
        log.info("::updateCategory started for category {}", category);

        if (category == null || category.getId() == null) {
            log.error("::updateCategory error: Category and its ID must not be null");
            throw new IllegalArgumentException("Category and its ID must not be null");
        }

        try {
            categoryValidator.existsByIdOrThrow(category.getId());
            Category updatedCategory = categoryRepository.save(category);
            log.info("::updateCategory completed successfully for category {}", updatedCategory);
            return updatedCategory;
        } catch (BusinessEntityNotFoundException e) {
            log.error("::updateCategory failed - Category not found: {}", category.getId());
            throw e;
        } catch (Exception e) {
            log.error("::updateCategory unexpected error for categoryId {}: {}", category.getId(), e.getMessage());
            throw new RuntimeException("Unexpected error while updating category " + category.getId(), e);
        }
    }

    @Override
    public void deleteCategoryById(final Long categoryId) {
        log.info("::deleteCategoryById started for categoryId {}", categoryId);

        try {
            categoryValidator.existsByIdOrThrow(categoryId);

//            if (productService.existsByCategoryId(categoryId)) {
//                log.warn("::deleteCategoryById aborted - Category {} has associated products", categoryId);
//                throw new IllegalStateException("Cannot delete category with existing products");
//            }

            categoryRepository.deleteById(categoryId);

            log.info("::deleteCategoryById completed successfully for categoryId {}", categoryId);
        } catch (BusinessEntityNotFoundException e) {
            log.error("::deleteCategoryById failed - Category not found: {}", categoryId);
            throw e;
        } catch (Exception e) {
            log.error("::deleteCategoryById error for categoryId {}: {}", categoryId, e.getMessage());
            throw new RuntimeException("Unexpected error while deleting category " + categoryId, e);
        }
    }



    @Override
    public Optional<Category> getCategoryByName(final String name) {
        log.info("::getCategoryByName started for category name {}", name);

        Optional<Category> category = categoryRepository.findByName(name);

        log.info("::getCategoryByName completed with result {} for category name {}", category.isPresent(), name);
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        log.info("::getAllCategories started");

        List<Category> categories = categoryRepository.findAll();

        log.info("::getAllCategories completed with {} categories found", categories.size());
        return categories;
    }
}
