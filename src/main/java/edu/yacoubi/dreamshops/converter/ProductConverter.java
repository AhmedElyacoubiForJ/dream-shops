package edu.yacoubi.dreamshops.converter;

import edu.yacoubi.dreamshops.dto.product.ProductRequestDTO;
import edu.yacoubi.dreamshops.dto.product.ProductResponseDTO;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public Product toEntity(ProductRequestDTO dto, Category category) {
        return Product.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .price(dto.getPrice())
                .inventory(dto.getInventory())
                .description(dto.getDescription())
                .category(category)
                .build();
    }

    public ProductResponseDTO toDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .inventory(product.getInventory())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
    }
}

