package edu.yacoubi.dreamshops.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "The ID of the category. This field is optional as it can also be provided as a path variable.")
    private Long categoryId;
}
