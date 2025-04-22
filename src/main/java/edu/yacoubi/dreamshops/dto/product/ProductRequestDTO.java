package edu.yacoubi.dreamshops.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequestDTO {
    @NotBlank(message = "Product name is mandatory")
    private String name;

    @NotBlank(message = "Brand is mandatory")
    private String brand;

    @NotBlank(message = "Price is mandatory")
    private BigDecimal price;

    @NotBlank(message = "Inventory is mandatory")
    private int inventory;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Category id is mandatory")
    private Long categoryId;
}
