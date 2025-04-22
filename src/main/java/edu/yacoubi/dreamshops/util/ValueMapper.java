package edu.yacoubi.dreamshops.util;

import edu.yacoubi.dreamshops.dto.product.ProductRequestDTO;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.model.Product;

public class ValueMapper {
    public static Product convertToEntity(ProductRequestDTO requestDTO, Category category) {
        return Product.builder()
                .name(requestDTO.getName())
                .brand(requestDTO.getBrand())
                .price(requestDTO.getPrice())
                .inventory(requestDTO.getInventory())
                .description(requestDTO.getDescription())
                .category(category)
                .build();
    }
}
