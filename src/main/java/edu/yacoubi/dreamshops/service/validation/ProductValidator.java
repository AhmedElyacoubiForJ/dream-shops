package edu.yacoubi.dreamshops.service.validation;

import edu.yacoubi.dreamshops.exception.BusinessEntityNotFoundException;
import edu.yacoubi.dreamshops.exception.ProductNotActiveException;
import edu.yacoubi.dreamshops.exception.ProductOutOfStockException;
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
    public Product getValidatedOrThrow(Long productId) {
        log.info("::getValidatedOrThrow started for productId {}", productId);
        log.debug("Checking existence of product ID: {}", productId);

        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("::getValidatedOrThrow error: Product with ID {} not found.", productId);
                    return new BusinessEntityNotFoundException("Product", productId);
                });
    }

    @Override
    public void existsByIdOrThrow(Long productId) {
        log.info("::existsByIdOrThrow started for productId {}", productId);
        log.debug("Checking if product exists for ID: {}", productId);

        if (!productRepository.existsById(productId)) {
            log.error("::existsByIdOrThrow error: Product with ID {} does not exist.", productId);
            throw new BusinessEntityNotFoundException("Product", productId);
        }
    }

    public void validateProductStatus(Product product) {
        log.info("::validateProductStatus started for productId {}", product.getId());
        log.debug("Checking status of product ID: {}", product.getId());

        if (!Status.ACTIVE.equals(product.getStatus())) {
            log.error("::validateProductStatus error: Product ID {} is not available.", product.getId());
            throw new ProductNotActiveException("Product ID " + product.getId() + " is not available.");
        }
    }

    public void validateInventory(Product product) {
        log.info("::validateInventory started for productId {}", product.getId());
        log.debug("Checking stock of product ID: {}", product.getId());

        if (product.getInventory() == 0) {
            log.error("::validateInventory error: Product ID {} is out of stock.", product.getId());
            throw new ProductOutOfStockException("Product ID " + product.getId() + " is out of stock.");
        }
    }
}
