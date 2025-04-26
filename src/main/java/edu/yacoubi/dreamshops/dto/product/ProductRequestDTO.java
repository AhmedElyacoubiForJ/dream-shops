package edu.yacoubi.dreamshops.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Inventory is mandatory")
    @Positive(message = "Inventory must be greater than zero")
    private int inventory;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Category id is mandatory")
    private Long categoryId;
}
