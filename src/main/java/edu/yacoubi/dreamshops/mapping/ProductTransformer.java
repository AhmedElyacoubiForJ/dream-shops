package edu.yacoubi.dreamshops.mapping;

import edu.yacoubi.dreamshops.dto.product.ProductCreateDTO;
import edu.yacoubi.dreamshops.dto.product.ProductResponseDTO;
import edu.yacoubi.dreamshops.dto.product.ProductUpdateDTO;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductTransformer implements EntityMapper<Product, ProductResponseDTO>,
        UpdatableEntity<Product, ProductUpdateDTO> {

    @Override
    public Product toEntity(ProductResponseDTO dto) {
        throw new UnsupportedOperationException("Use specific DTO methods for creation.");
    }

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

    @Override
    public void updateEntity(Product existingProduct, ProductUpdateDTO dto) {
        existingProduct.setName(dto.getName());
        existingProduct.setBrand(dto.getBrand());
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setInventory(dto.getInventory());
        existingProduct.setDescription(dto.getDescription());
    }

    @Override
    public ProductResponseDTO toDTO(Product entity) {
        return ProductResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .price(entity.getPrice())
                .inventory(entity.getInventory())
                .description(entity.getDescription())
                .categoryId(entity.getCategory().getId())
                .build();
    }
}