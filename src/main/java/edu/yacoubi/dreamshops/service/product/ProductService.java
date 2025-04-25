package edu.yacoubi.dreamshops.service.product;

import edu.yacoubi.dreamshops.dto.product.ProductRequestDTO;
import edu.yacoubi.dreamshops.exceptions.ProductNotFoundException;
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
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        return productValidator.getValidatedOrThrow(productId);
    }

    @Override
    public Product updateProduct(ProductRequestDTO product, Long productId) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.findById(productId)
                .ifPresentOrElse(
                        productRepository::delete,
                        () -> {
                            throw new ProductNotFoundException("Product not found!");
                        }
                );
    }

    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    public void reduceInventory(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produkt nicht gefunden!"));

        if (product.getInventory() - quantity < 0) {
            throw new IllegalArgumentException("Nicht genügend Bestand verfügbar!");
        }

        product.setInventory(product.getInventory() - quantity);
        productRepository.save(product);
    }

    @Override
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return List.of();
    }
}
