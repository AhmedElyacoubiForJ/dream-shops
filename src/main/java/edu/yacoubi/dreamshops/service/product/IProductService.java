package edu.yacoubi.dreamshops.service.product;

import edu.yacoubi.dreamshops.model.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductByIdOrThrow(Long productId);

    Product updateProduct(Product product);

    void deleteProductOrThrow(Long productId);

    List<Product> getProductsByCategoryName(String categoryName);

    List<Product> getProductsByBrand(String brandName);

    List<Product> getProductsByCategoryAndBrand(String categoryName, String brandName);

    List<Product> getProductsByName(String productName);

    List<Product> getProductsByBrandAndName(String brandName, String productName);

    Long countProductsByBrandAndName(String brandName, String productName);

    void reduceInventoryOrThrow(Long productId, int quantity);

    List<Product> getProductsByPriceRange(double minPrice, double maxPrice);
}
