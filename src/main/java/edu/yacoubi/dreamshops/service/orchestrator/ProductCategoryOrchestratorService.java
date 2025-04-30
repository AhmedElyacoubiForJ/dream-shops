package edu.yacoubi.dreamshops.service.orchestrator;

import edu.yacoubi.dreamshops.dto.product.ProductCreateDTO;
import edu.yacoubi.dreamshops.dto.product.ProductUpdateDTO;
import edu.yacoubi.dreamshops.exception.BusinessEntityNotFoundException;
import edu.yacoubi.dreamshops.mapping.ProductTransformer;
import edu.yacoubi.dreamshops.model.Category;
import edu.yacoubi.dreamshops.model.Product;
import edu.yacoubi.dreamshops.service.category.CategoryService;
import edu.yacoubi.dreamshops.service.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryOrchestratorService
        implements IProductCategoryOrchestratorService {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductTransformer productTransformer;

    @Override
    public Product createProductForCategory(ProductCreateDTO productCreateDTO, Long categoryId) {
        log.info("::createProductForCategory started with productDTO: {}, categoryId: {}", productCreateDTO, categoryId);

        validateCreateProductForCategory(productCreateDTO, categoryId);

        try {
            Category foundCategory = categoryService.getCategoryByIdOrThrow(categoryId);
            Product product = productTransformer.toEntity(productCreateDTO, foundCategory);
            Product savedProduct = productService.addProduct(product);

            log.info("::createProductForCategory completed successfully with product: {}", savedProduct);
            return savedProduct;
        } catch (BusinessEntityNotFoundException e) {
            log.warn("::createProductForCategory failed - Category not found: {}", categoryId);
            throw e;
        } catch (Exception e) {
            log.error("::createProductForCategory failed for categoryId {}: {}", categoryId, e.getMessage());
            throw new RuntimeException("Unexpected error while creating product for category " + categoryId, e);
        }
    }

    private void validateCreateProductForCategory(ProductCreateDTO productCreateDTO, Long categoryId) {
        Objects.requireNonNull(productCreateDTO, "ProductCreateDTO must not be null");
        Objects.requireNonNull(categoryId, "Category ID must not be null");
    }


    @Override
    @Transactional
    public Product updateProduct(ProductUpdateDTO request, Long productId) {
        log.info("::updateProduct started with request {} and productId {}", request, productId);

        try {
            Category newCategory = categoryService.getCategoryByIdOrThrow(request.getCategoryId());
            Product existingProduct = productService.getProductByIdOrThrow(productId);

            productTransformer.updateEntity(existingProduct, request);
            existingProduct.setCategory(newCategory); // Kategorie zuweisen

            Product updatedProduct = productService.updateProduct(existingProduct);
            log.info("::updateProduct completed successfully for productId {}", productId);

            return updatedProduct;
        } catch (Exception e) {
            log.error("::updateProduct error for productId {}: {}", productId, e.getMessage());
            throw e;
        }
    }
}
