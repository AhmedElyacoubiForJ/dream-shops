package edu.yacoubi.dreamshops.service.orchestrator;

import edu.yacoubi.dreamshops.dto.product.ProductRequestDTO;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.model.Product;
import edu.yacoubi.dreamshops.service.category.CategoryService;
import edu.yacoubi.dreamshops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryOrchestratorService
        implements IProductCategoryOrchestratorService {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Override
    public Product createProductForCategory(final ProductRequestDTO productDTO, final Long categoryId) {
        if (log.isInfoEnabled()) {
            log.info("::createProductForCategory started with: productDTO {}, categoryId {}", productDTO, categoryId);
        }

        try {
            final Category foundCategory = categoryService.getCategoryByIdOrThrow(categoryId);

            Product product = Product.builder()
                    .name(productDTO.getName())
                    .brand(productDTO.getBrand())
                    .price(productDTO.getPrice())
                    .inventory(productDTO.getInventory())
                    .description(productDTO.getDescription())
                    .category(foundCategory)
                    .build();

            final Product savedProduct = productService.addProduct(product);

            if (log.isInfoEnabled()) {
                log.info("::createProductForCategory completed successfully with product {}", savedProduct);
            }

            return savedProduct;
        } catch (Exception e) {
            log.error("::createProductForCategory error for categoryId {}: {}", categoryId, e.getMessage());
            throw e;
        }
    }
}
