package edu.yacoubi.dreamshops.service.validation;

import edu.yacoubi.dreamshops.exceptions.ProductNotAvailableException;
import edu.yacoubi.dreamshops.exceptions.ProductNotFoundException;
import edu.yacoubi.dreamshops.exceptions.ProductOutOfStockException;
import edu.yacoubi.dreamshops.model.Product;
import edu.yacoubi.dreamshops.model.Status;
import edu.yacoubi.dreamshops.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductValidator implements BaseValidator<Product, Long> {

    private final ProductRepository productRepository;

    @Override
    public Product validateExists(final Long productId) {
        if (log.isInfoEnabled()) {
            log.info("::validateExists started for productId {}", productId);
        }

        if (log.isDebugEnabled()) {
            log.debug("Checking existence of product ID: {}", productId);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    String errorMessage = "Product not found with ID: " + productId;
                    log.error("::validateExists error: {}", errorMessage);
                    return new ProductNotFoundException(errorMessage);
                });

        if (log.isInfoEnabled()) {
            log.info("::validateExists completed successfully for productId {}", productId);
        }
        return product;
    }

    public void validateProductStatus(Product product) {
        if (log.isInfoEnabled()) {
            log.info("::validateProductStatus started for productId {}", product.getId());
        }

        if (log.isDebugEnabled()) {
            log.debug("Checking status of product ID: {}", product.getId());
        }

        if (!product.getStatus().equals(Status.ACTIVE)) {
            String errorMessage = "Product ID " + product.getId() + " is not available.";
            log.error("::validateProductStatus error: {}", errorMessage);
            throw new ProductNotAvailableException(errorMessage);
        }

        if (log.isInfoEnabled()) {
            log.info("::validateProductStatus completed successfully for productId {}", product.getId());
        }
    }

    public void validateInventory(Product product) {
        if (log.isInfoEnabled()) {
            log.info("::validateInventory started for productId {}", product.getId());
        }

        if (log.isDebugEnabled()) {
            log.debug("Checking stock of product ID: {}", product.getId());
        }

        if (product.getInventory() == 0) {
            String errorMessage = "Product ID " + product.getId() + " is out of stock.";
            log.error("::validateInventory error: {}", errorMessage);
            throw new ProductOutOfStockException(errorMessage);
        }

        if (log.isInfoEnabled()) {
            log.info("::validateInventory completed successfully for productId {}", product.getId());
        }
    }
}
