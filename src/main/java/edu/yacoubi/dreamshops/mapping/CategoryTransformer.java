package edu.yacoubi.dreamshops.mapping;

public class CategoryTransformer {
}


/*
*
* package edu.yacoubi.dreamshops.mapping;

import edu.yacoubi.dreamshops.dto.category.CategoryDTO;
import edu.yacoubi.dreamshops.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryTransformer implements EntityMapper<Category, CategoryDTO> {

    @Override
    public Category toEntity(CategoryDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public CategoryDTO toDTO(Category entity) {
        return CategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}

*
* */