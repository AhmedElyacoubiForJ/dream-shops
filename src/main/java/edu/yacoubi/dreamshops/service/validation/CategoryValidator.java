package edu.yacoubi.dreamshops.service.validation;

import edu.yacoubi.dreamshops.exceptions.CategoryNotFoundException;
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
    public Category getValidatedOrThrow(final Long categoryId) {
        if (log.isInfoEnabled()) {
            log.info("::getValidatedOrThrow started for categoryId {}", categoryId);

        }

        if (log.isDebugEnabled()) {
            log.debug("Checking existence of category ID: {}", categoryId);
        }

        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    String errorMessage = "Category not found with ID: " + categoryId;
                    log.error("::getValidatedOrThrow error: {}", errorMessage);
                    return new CategoryNotFoundException(errorMessage);
                });

        if (log.isInfoEnabled()) {
            log.info("::getValidatedOrThrow completed successfully for categoryId {}", categoryId);
        }
        return category;
    }

    @Override
    public void checkExistsOrThrow(final Long categoryId) {
        if (log.isInfoEnabled()) {
            log.info("::checkExistsOrThrow started for categoryId {}", categoryId);
        }

        if (log.isDebugEnabled()) {
            log.debug("Checking if category exists for ID: {}", categoryId);
        }

        final boolean exists = categoryRepository.existsById(categoryId);

        if (!exists) {
            String errorMessage = "Category with ID " + categoryId + " does not exist.";
            log.error("::checkExistsOrThrow error: {}", errorMessage);
            throw new CategoryNotFoundException(errorMessage);
        }

        if (log.isInfoEnabled()) {
            log.info("::checkExistsOrThrow completed successfully for categoryId {}", categoryId);
        }
    }
}
