package edu.yacoubi.dreamshops.service.product;

import edu.yacoubi.dreamshops.dto.product.ProductRequestDTO;
import edu.yacoubi.dreamshops.model.Product;

import java.util.List;

public interface IProductService {
    Product createProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Long productId);

    Product updateProduct(ProductRequestDTO product, Long productId);

    void deleteProduct(Long productId);

    List<Product> getProductsByCategoryName(String categoryName);

    List<Product> getProductsByBrand(String brandName);

    List<Product> getProductsByCategoryAndBrand(String categoryName, String brandName);

    List<Product> getProductsByName(String productName);

    List<Product> getProductsByBrandAndName(String brandName, String productName);

    Long countProductsByBrandAndName(String brandName, String productName);

    void reduceInventory(Long productId, int quantity);
}
