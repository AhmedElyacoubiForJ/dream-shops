package edu.yacoubi.dreamshops.service.category;

import edu.yacoubi.dreamshops.exceptions.CategoryNotFoundException;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not Found with ID: " + categoryId));
    }
}
