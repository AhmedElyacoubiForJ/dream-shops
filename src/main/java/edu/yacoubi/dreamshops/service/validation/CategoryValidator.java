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
    public Category validateExists(Long categoryId) {
        if (log.isInfoEnabled()) {
            log.info("::validateExists started for categoryId {}", categoryId);

        }

        if (log.isDebugEnabled()) {
            log.debug("Checking existence of category ID: {}", categoryId);
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    String errorMessage = "Category not found with ID: " + categoryId;
                    log.error("::validateExists error: {}", errorMessage);
                    return new CategoryNotFoundException(errorMessage);
                });

        if (log.isInfoEnabled()) {
            log.info("::validateExists completed successfully for categoryId {}", categoryId);
        }
        return category;
    }
}
