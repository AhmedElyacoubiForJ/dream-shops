package edu.yacoubi.dreamshops.service.init;

import edu.yacoubi.dreamshops.dto.product.ProductRequestDTO;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.service.category.CategoryService;
import edu.yacoubi.dreamshops.service.orchestrator.ProductCategoryOrchestratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializerService {

    private final CategoryService categoryService;
    private final ProductCategoryOrchestratorService productCategoryOrchestratorService;

    @EventListener(ContextRefreshedEvent.class)
    public void initDefaultData() {
        log.info(":: Initializing database with default categories and products");

        Long categoryId = createCategoryIfNotExists("Elektronik", "Alle Arten von Elektronikprodukten");
        createProductForCategory(categoryId);

        log.info(":: Default categories and products created successfully");
    }

    private Long createCategoryIfNotExists(String name, String description) {
        Category category = categoryService.getCategoryByName(name).orElseGet(() -> {
            Category newCategory = new Category(name, description);
            return categoryService.addCategory(newCategory);
        });
        log.info("Kategorie erstellt oder bereits vorhanden: {}", category.getName());
        return category.getId();
    }

    private void createProductForCategory(Long categoryId) {
        ProductRequestDTO productDTO = ProductRequestDTO.builder()
                .name("Smartphone")
                .brand("Samsung")
                .price(BigDecimal.valueOf(999.99))
                .inventory(50)
                .description("High-end Smartphone mit top Kamera")
                .build();

        productCategoryOrchestratorService.createProductForCategory(productDTO, categoryId);
        log.info("Produkt erfolgreich in Kategorie {} erstellt!", categoryId);
    }
}
