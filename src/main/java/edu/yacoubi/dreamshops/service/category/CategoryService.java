package edu.yacoubi.dreamshops.service.category;

import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.repository.CategoryRepository;
import edu.yacoubi.dreamshops.service.validation.CategoryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        Category foundCategory = categoryValidator.getValidated(categoryId);

        if (log.isInfoEnabled()) {
            log.info("::getCategoryById completed successfully for categoryId {}", categoryId);
        }
        return foundCategory;
    }

    // weitere methoden
}
