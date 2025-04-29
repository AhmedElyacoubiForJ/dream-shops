package edu.yacoubi.dreamshops.dto.product;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductPatchDTO {
    private Long id;
    private String name;
    private String brand;

    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @Positive(message = "Inventory must be greater than zero")
    private Integer inventory;

    private String description;
    private Long categoryId;
}
