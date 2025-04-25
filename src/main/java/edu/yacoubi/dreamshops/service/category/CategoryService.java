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
    public Category getCategoryById(Long categoryId) {
        if (log.isInfoEnabled()) {
            log.info("::getCategoryById started for categoryId {}", categoryId);
        }

        try {
            Category foundCategory = categoryValidator.getValidatedOrThrow(categoryId);
            log.info("::getCategoryById completed successfully for categoryId {}", categoryId);

            return foundCategory;
        } catch (Exception e) {
            log.error("::getCategoryById error for categoryId {}: {}", categoryId, e.getMessage());
            throw e;
        }
    }

//    @Override
//    public boolean existsByName(String name) {
//        if (log.isInfoEnabled()) {
//            log.info("::existsByName started for category name {}", name);
//        }
//
//        boolean exists = categoryRepository.existsByName(name);
//
//        if (log.isInfoEnabled()) {
//            log.info("::existsByName completed with result {} for category name {}", exists, name);
//        }
//
//        return exists;
//    }

    @Override
    public Category addCategory(Category category) {
        if (log.isInfoEnabled()) {
            log.info("::addCategory started for category {}", category);
        }

        if (category == null || category.getName() == null) {
            String errorMessage = "Category name must not be null";
            log.error("::addCategory error: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        String categoryName = category.getName();
        if (categoryValidator.existsByName(categoryName)) {
            String errorMessage = "Entity already exists with name: " + categoryName;
            log.error("::addCategory error: {}", errorMessage);
            throw new DuplicateEntityException(errorMessage);
        }

        Category savedCategory = categoryRepository.save(category);

        if (log.isInfoEnabled()) {
            log.info("::addCategory completed successfully for category {}", savedCategory);
        }

        return savedCategory;
    }


    @Override
    public Optional<Category> getCategoryByName(String name) {
        if (log.isInfoEnabled()) {
            log.info("::getCategoryByName started for category name {}", name);
        }

        Optional<Category> category = categoryRepository.findByName(name);

        if (log.isInfoEnabled()) {
            log.info("::getCategoryByName completed with result {} for category name {}", category.isPresent(), name);
        }

        return category;
    }

    // weitere methoden
}
