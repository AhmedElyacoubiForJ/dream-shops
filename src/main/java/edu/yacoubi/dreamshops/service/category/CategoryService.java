package edu.yacoubi.dreamshops.service.category;

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

        Category foundCategory = categoryValidator.getValidatedOrThrow(categoryId);

        if (log.isInfoEnabled()) {
            log.info("::getCategoryById completed successfully for categoryId {}", categoryId);
        }
        return foundCategory;
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public Category createCategory(Category category) {
        if (category == null || category.getName() == null) {
            throw new IllegalArgumentException("Category name must not be null");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    // weitere methoden
}
