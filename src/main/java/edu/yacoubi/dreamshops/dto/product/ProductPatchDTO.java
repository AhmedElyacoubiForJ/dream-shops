package edu.yacoubi.dreamshops.dto.product;

import edu.yacoubi.dreamshops.model.Category;

import java.math.BigDecimal;

public class ProductPatchDTO {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
