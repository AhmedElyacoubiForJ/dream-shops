package edu.yacoubi.dreamshops.service.validation;

import edu.yacoubi.dreamshops.exception.BusinessEntityNotFoundException;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryValidator implements BaseValidator<Category, Long> {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getValidatedOrThrow(Long categoryId) {
        log.info("::getValidatedOrThrow started for categoryId {}", categoryId);
        log.debug("Checking existence of category ID: {}", categoryId);

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("::getValidatedOrThrow error: Category with ID {} not found.", categoryId);
                    return new BusinessEntityNotFoundException("Category", categoryId);
                });
    }

    @Override
    public void existsByIdOrThrow(Long categoryId) {
        log.info("::existsByIdOrThrow started for categoryId {}", categoryId);
        log.debug("Checking if category exists for ID: {}", categoryId);

        if (!categoryRepository.existsById(categoryId)) {
            log.error("::existsByIdOrThrow error: Category with ID {} does not exist.", categoryId);
            throw new BusinessEntityNotFoundException("Category", categoryId);
        }
    }

    public boolean existsByName(String name) {
        log.info("::existsByName started for category name {}", name);

        boolean exists = categoryRepository.existsByName(name);

        log.info("::existsByName completed with result {} for category name {}", exists, name);
        return exists;
    }
}
