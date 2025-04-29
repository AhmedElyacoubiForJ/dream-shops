package edu.yacoubi.dreamshops.dto.product;

import edu.yacoubi.dreamshops.dto.image.ImageResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Long categoryId; // Verhindert direkte Entity-Abhängigkeit
    private List<ImageResponseDTO> images;
}
