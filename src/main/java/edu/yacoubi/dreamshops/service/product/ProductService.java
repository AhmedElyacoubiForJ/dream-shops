package edu.yacoubi.dreamshops.service.product;

import edu.yacoubi.dreamshops.dto.product.ProductRequestDTO;
import edu.yacoubi.dreamshops.model.Product;
import edu.yacoubi.dreamshops.repository.ProductRepository;
import edu.yacoubi.dreamshops.service.validation.ProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    @Override
    public Product addProduct(final Product product) {
        if (log.isInfoEnabled()) {
            log.info("::addProduct started with: product {}", product);
        }

        if (product == null || product.getCategory() == null) {
            throw new IllegalArgumentException("Product must not be null and must have a valid category.");
        }

        product.setId(null);
        final Product savedProduct = productRepository.save(product);

        if (log.isInfoEnabled()) {
            log.info("::addProduct completed successfully");
        }
        return savedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        if (log.isInfoEnabled()) {
            log.info("::getAllProducts started");
        }

        final List<Product> products = productRepository.findAll();

        if (log.isInfoEnabled()) {
            log.info("::getAllProducts completed with {} products found", products.size());
        }

        return products;
    }

    @Override
    public Product getProductByIdOrThrow(final Long productId) {
        if (log.isInfoEnabled()) {
            log.info("::getProductByIdOrThrow started for productId {}", productId);
        }

        try {
            final Product product = productValidator.getValidatedOrThrow(productId);

            if (log.isInfoEnabled()) {
                log.info("::getProductByIdOrThrow completed successfully for productId {}", productId);
            }

            return product;
        } catch (Exception e) {
            log.error("::getProductByIdOrThrow error for productId {}: {}", productId, e.getMessage());
            throw e;
        }
    }

    @Override
    public Product updateProduct(ProductRequestDTO product, Long productId) {
        return null;
    }

    @Override
    public void deleteProductOrThrow(final Long productId) {
        if (log.isInfoEnabled()) {
            log.info("::deleteProduct started for productId {}", productId);
        }

        try {
            final Product product = productValidator.getValidatedOrThrow(productId);
            productRepository.delete(product);

            if (log.isInfoEnabled()) {
                log.info("::deleteProduct completed successfully for productId {}", productId);
            }
        } catch (Exception e) {
            log.error("::deleteProduct error for productId {}: {}", productId, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> getProductsByCategoryName(final String categoryName) {
        if (log.isInfoEnabled()) {
            log.info("::getProductsByCategoryName started for categoryName {}", categoryName);
        }

        final List<Product> products = productRepository.findByCategoryName(categoryName);

        if (log.isInfoEnabled()) {
            log.info("::getProductsByCategoryName completed with {} products found for categoryName {}", products.size(), categoryName);
        }

        return products;
    }

    @Override
    public List<Product> getProductsByBrand(final String brand) {
        if (log.isInfoEnabled()) {
            log.info("::getProductsByBrand started for brand {}", brand);
        }

        final List<Product> products = productRepository.findByBrand(brand);

        if (log.isInfoEnabled()) {
            log.info("::getProductsByBrand completed with {} products found for brand {}", products.size(), brand);
        }

        return products;
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(final String category, final String brand) {
        if (log.isInfoEnabled()) {
            log.info("::getProductsByCategoryAndBrand started for category {} and brand {}", category, brand);
        }

        final List<Product> products = productRepository.findByCategoryNameAndBrand(category, brand);

        if (log.isInfoEnabled()) {
            log.info("::getProductsByCategoryAndBrand completed with {} products found for category {} and brand {}",
                    products.size(), category, brand);
        }

        return products;
    }

    @Override
    public List<Product> getProductsByName(final String name) {
        if (log.isInfoEnabled()) {
            log.info("::getProductsByName started for name {}", name);
        }

        final List<Product> products = productRepository.findByName(name);

        if (log.isInfoEnabled()) {
            log.info("::getProductsByName completed with {} products found for name {}", products.size(), name);
        }

        return products;
    }

    @Override
    public List<Product> getProductsByBrandAndName(final String brand, final String name) {
        if (log.isInfoEnabled()) {
            log.info("::getProductsByBrandAndName started for brand {} and name {}", brand, name);
        }

        final List<Product> products = productRepository.findByBrandAndName(brand, name);

        if (log.isInfoEnabled()) {
            log.info("::getProductsByBrandAndName completed with {} products found for brand {} and name {}",
                    products.size(), brand, name);
        }

        return products;
    }

    @Override
    public Long countProductsByBrandAndName(final String brand, final String name) {
        if (log.isInfoEnabled()) {
            log.info("::countProductsByBrandAndName started for brand {} and name {}", brand, name);
        }

        final Long count = productRepository.countByBrandAndName(brand, name);

        if (log.isInfoEnabled()) {
            log.info("::countProductsByBrandAndName completed with {} products counted for brand {} and name {}",
                    count, brand, name);
        }

        return count;
    }

    @Override
    public void reduceInventoryOrThrow(final Long productId, final int quantity) {
        if (log.isInfoEnabled()) {
            log.info("::reduceInventory started for productId {} with quantity {}", productId, quantity);
        }

        try {
            final Product product = productValidator.getValidatedOrThrow(productId);

            if (product.getInventory() - quantity < 0) {
                String errorMessage = "Insufficient inventory for productId " + productId;
                log.error("::reduceInventory error: {}", errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            product.setInventory(product.getInventory() - quantity);
            productRepository.save(product);

            if (log.isInfoEnabled()) {
                log.info("::reduceInventory completed successfully for productId {}", productId);
            }
        } catch (Exception e) {
            log.error("::reduceInventory error for productId {}: {}", productId, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> getProductsByPriceRange(final double minPrice, final double maxPrice) {
        if (log.isInfoEnabled()) {
            log.info("::getProductsByPriceRange started for minPrice {} and maxPrice {}", minPrice, maxPrice);
        }

        final List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);

        if (log.isInfoEnabled()) {
            log.info("::getProductsByPriceRange completed with {} products found for range {} - {}",
                    products.size(), minPrice, maxPrice);
        }

        return products;
    }
}
