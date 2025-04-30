package edu.yacoubi.dreamshops.mapping;

import edu.yacoubi.dreamshops.dto.product.ProductCreateDTO;
import edu.yacoubi.dreamshops.dto.product.ProductResponseDTO;
import edu.yacoubi.dreamshops.dto.product.ProductUpdateDTO;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductTransformer {
    public Product toEntity(ProductCreateDTO dto, Category category) {
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

    public void updateEntity(Product existingProduct, ProductUpdateDTO dto) {
        existingProduct.setName(dto.getName());
        existingProduct.setBrand(dto.getBrand());
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setInventory(dto.getInventory());
        existingProduct.setDescription(dto.getDescription());
    }
}

