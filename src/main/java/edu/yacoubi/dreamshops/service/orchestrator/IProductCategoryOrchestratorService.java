package edu.yacoubi.dreamshops.service.orchestrator;

import edu.yacoubi.dreamshops.dto.product.ProductCreateDTO;
import edu.yacoubi.dreamshops.model.Product;

public interface IProductCategoryOrchestratorService {
    Product createProductForCategory(ProductCreateDTO productCreateDTO, Long categoryId);
    Product updateProduct(ProductCreateDTO productDTO, Long productId);
}
