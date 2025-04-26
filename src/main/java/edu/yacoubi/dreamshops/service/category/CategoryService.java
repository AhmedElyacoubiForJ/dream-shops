package edu.yacoubi.dreamshops.service.category;

import edu.yacoubi.dreamshops.exceptions.DuplicateEntityException;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.repository.CategoryRepository;
import edu.yacoubi.dreamshops.service.validation.CategoryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;

    @Override
    public Category getCategoryByIdOrThrow(final Long categoryId) {
        if (log.isInfoEnabled()) {
            log.info("::getCategoryById started for categoryId {}", categoryId);
        }

        try {
            final Category foundCategory = categoryValidator.getValidatedOrThrow(categoryId);
            log.info("::getCategoryById completed successfully for categoryId {}", categoryId);

            return foundCategory;
        } catch (Exception e) {
            log.error("::getCategoryById error for categoryId {}: {}", categoryId, e.getMessage());
            throw e;
        }
    }

    @Override
    public Category addCategory(final Category category) {
        if (log.isInfoEnabled()) {
            log.info("::addCategory started for category {}", category);
        }

        if (category == null || category.getName() == null) {
            String errorMessage = "Category name must not be null";
            log.error("::addCategory error: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        final String categoryName = category.getName();
        if (categoryValidator.existsByName(categoryName)) {
            final String errorMessage = "Entity already exists with name: " + categoryName;
            log.error("::addCategory error: {}", errorMessage);
            throw new DuplicateEntityException(errorMessage);
        }

        final Category savedCategory = categoryRepository.save(category);

        if (log.isInfoEnabled()) {
            log.info("::addCategory completed successfully for category {}", savedCategory);
        }

        return savedCategory;
    }


    @Override
    public Optional<Category> getCategoryByName(final String name) {
        if (log.isInfoEnabled()) {
            log.info("::getCategoryByName started for category name {}", name);
        }

        final Optional<Category> category = categoryRepository.findByName(name);

        if (log.isInfoEnabled()) {
            log.info("::getCategoryByName completed with result {} for category name {}", category.isPresent(), name);
        }

        return category;
    }

    // weitere methoden
}
