package edu.yacoubi.dreamshops.service.orchestrator;

import edu.yacoubi.dreamshops.converter.ProductConverter;
import edu.yacoubi.dreamshops.dto.product.ProductCreateDTO;
import edu.yacoubi.dreamshops.exception.CategoryNotFoundException;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.model.Product;
import edu.yacoubi.dreamshops.service.category.CategoryService;
import edu.yacoubi.dreamshops.service.product.ProductService;
import jakarta.transaction.Transactional;
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
    private final ProductConverter productConverter;

    @Override
    public Product createProductForCategory(ProductCreateDTO productCreateDTO, Long categoryId) {
        log.info("::createProductForCategory started with: productDTO {}, categoryId {}", productCreateDTO, categoryId);

        try {
            Category foundCategory = categoryService.getCategoryByIdOrThrow(categoryId);
            Product product = productConverter.toEntity(productCreateDTO, foundCategory);
            Product savedProduct = productService.addProduct(product);

            log.info("::createProductForCategory completed successfully with product {}", savedProduct);
            return savedProduct;
        } catch (CategoryNotFoundException e) {
            log.error("::createProductForCategory failed - Category not found: {}", categoryId);
            throw e;
        } catch (Exception e) {
            log.error("::createProductForCategory failed for categoryId {}: {}", categoryId, e.getMessage());
            throw new RuntimeException("Unexpected error while creating product for category " + categoryId, e);
        }
    }


    @Override
    @Transactional
    public Product updateProduct(final ProductCreateDTO productDTO, final Long productId) {
        if (log.isInfoEnabled()) {
            log.info("::updateProduct started with productDTO {} and productId {}", productDTO, productId);
        }

        try {
            final Category newCategory = categoryService.getCategoryByIdOrThrow(productDTO.getCategoryId());
            final Product existingProduct = productService.getProductByIdOrThrow(productId);

            // Produkt-Daten aktualisieren
            existingProduct.setName(productDTO.getName());
            existingProduct.setBrand(productDTO.getBrand());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setInventory(productDTO.getInventory());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setCategory(newCategory); // Kategorie zuweisen

            final Product updatedProduct = productService.updateProduct(existingProduct);

            if (log.isInfoEnabled()) {
                log.info("::updateProduct completed successfully for productId {}", productId);
            }

            return updatedProduct;
        } catch (Exception e) {
            log.error("::updateProduct error for productId {}: {}", productId, e.getMessage());
            throw e;
        }
    }
}
