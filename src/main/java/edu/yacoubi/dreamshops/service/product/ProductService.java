package edu.yacoubi.dreamshops.service.product;

import edu.yacoubi.dreamshops.model.Product;
import edu.yacoubi.dreamshops.repository.ProductRepository;
import edu.yacoubi.dreamshops.service.validation.ProductValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    @Override
    public Product addProduct(final Product product) {
        log.info("::addProduct started with product: {}", product);

        validateProduct(product);

        product.setId(null);
        final Product savedProduct = productRepository.save(product);

        log.info("::addProduct completed successfully with product: {}", savedProduct);
        return savedProduct;
    }

    private void validateProduct(Product product) {
        Objects.requireNonNull(product, "Product must not be null");
        Objects.requireNonNull(product.getCategory(), "Product must have a valid category");
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("::getAllProducts started");

        List<Product> products = productRepository.findAll();

        log.info("::getAllProducts completed - {} products found", products.size());
        return products;
    }

    @Override
    public Product getProductByIdOrThrow(final Long productId) {
        log.info("::getProductByIdOrThrow started for productId {}", productId);

        try {
            Product product = productValidator.getValidatedOrThrow(productId);

            log.info("::getProductByIdOrThrow completed successfully for productId {}", productId);
            return product;
        } catch (Exception e) {
            log.error("::getProductByIdOrThrow error for productId {}: {}", productId, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Product updateProduct(final Product product) {
        log.info("::updateProduct started for productId {}", product.getId());

        Product updatedProduct = productRepository.save(product);

        log.info("::updateProduct completed successfully for productId {}", updatedProduct.getId());
        return updatedProduct;
    }

    @Override
    public void deleteProductOrThrow(final Long productId) {
        log.info("::deleteProduct started for productId {}", productId);

        try {
            Product product = productValidator.getValidatedOrThrow(productId);
            productRepository.delete(product);

            log.info("::deleteProduct completed successfully for productId {}", productId);
        } catch (Exception e) {
            log.error("::deleteProduct error for productId {}: {}", productId, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> getProductsByCategoryName(final String categoryName) {
        log.info("::getProductsByCategoryName started for categoryName {}", categoryName);

        List<Product> products = productRepository.findByCategoryName(categoryName);

        log.info("::getProductsByCategoryName completed with {} products found for categoryName {}", products.size(), categoryName);
        return products;
    }

    @Override
    public List<Product> getProductsByBrand(final String brand) {
        log.info("::getProductsByBrand started for brand {}", brand);

        List<Product> products = productRepository.findByBrand(brand);

        log.info("::getProductsByBrand completed with {} products found for brand {}", products.size(), brand);
        return products;
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(final String category, final String brand) {
        log.info("::getProductsByCategoryAndBrand started for category {} and brand {}", category, brand);

        List<Product> products = productRepository.findByCategoryNameAndBrand(category, brand);

        log.info("::getProductsByCategoryAndBrand completed with {} products found for category {} and brand {}",
                products.size(), category, brand);

        return products;
    }

    @Override
    public List<Product> getProductsByName(final String name) {
        log.info("::getProductsByName started for name {}", name);

        List<Product> products = productRepository.findByName(name);

        log.info("::getProductsByName completed with {} products found for name {}", products.size(), name);
        return products;
    }

    @Override
    public List<Product> getProductsByBrandAndName(final String brand, final String name) {
        log.info("::getProductsByBrandAndName started for brand {} and name {}", brand, name);

        List<Product> products = productRepository.findByBrandAndName(brand, name);

        log.info("::getProductsByBrandAndName completed with {} products found for brand {} and name {}",
                products.size(), brand, name);

        return products;
    }

    @Override
    public Long countProductsByBrandAndName(final String brand, final String name) {
        log.info("::countProductsByBrandAndName started for brand {} and name {}", brand, name);

        Long count = productRepository.countByBrandAndName(brand, name);

        log.info("::countProductsByBrandAndName completed with {} products counted for brand {} and name {}",
                count, brand, name);

        return count;
    }

    @Override
    public void reduceInventoryOrThrow(final Long productId, final int quantity) {
        log.info("::reduceInventory started for productId {} with quantity {}", productId, quantity);

        try {
            Product product = productValidator.getValidatedOrThrow(productId);

            if (product.getInventory() < quantity) {
                log.error("::reduceInventory error: Insufficient inventory for productId {}", productId);
                throw new IllegalArgumentException("Insufficient inventory for productId " + productId);
            }

            product.setInventory(product.getInventory() - quantity);
            productRepository.save(product);

            log.info("::reduceInventory completed successfully for productId {}", productId);
        } catch (Exception e) {
            log.error("::reduceInventory error for productId {}: {}", productId, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> getProductsByPriceRange(final double minPrice, final double maxPrice) {
        log.info("::getProductsByPriceRange started for minPrice {} and maxPrice {}", minPrice, maxPrice);

        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);

        log.info("::getProductsByPriceRange completed with {} products found for range {} - {}",
                products.size(), minPrice, maxPrice);

        return products;
    }
}
