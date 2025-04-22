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
    public Product getValidated(final Long productId) {
        if (log.isInfoEnabled()) {
            log.info("::getValidated started for productId {}", productId);
        }

        if (log.isDebugEnabled()) {
            log.debug("Checking existence of product ID: {}", productId);
        }

        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    String errorMessage = "Product not found with ID: " + productId;
                    log.error("::getValidated error: {}", errorMessage);
                    return new ProductNotFoundException(errorMessage);
                });

        if (log.isInfoEnabled()) {
            log.info("::getValidated completed successfully for productId {}", productId);
        }
        return product;
    }

    @Override
    public void throwIfNotExists(Long productId) {
        if (log.isInfoEnabled()) {
            log.info("::throwIfNotExists started for productId {}", productId);
        }

        if (log.isDebugEnabled()) {
            log.debug("Checking if product exists for ID: {}", productId);
        }

        boolean exists = productRepository.existsById(productId);

        if (!exists) {
            String errorMessage = "Product with ID " + productId + " does not exist.";
            log.error("::throwIfNotExists error: {}", errorMessage);
            throw new ProductNotFoundException(errorMessage);
        }

        if (log.isInfoEnabled()) {
            log.info("::throwIfNotExists completed successfully for productId {}", productId);
        }
    }


    public void validateProductStatus(final Product product) {
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

    public void validateInventory(final Product product) {
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
