package edu.yacoubi.dreamshops.service.orchestrator;

import edu.yacoubi.dreamshops.dto.product.ProductCreateDTO;
import edu.yacoubi.dreamshops.model.Product;

public interface IProductCategoryOrchestratorService {
    Product createProductForCategory(ProductCreateDTO productDTO, Long categoryId);
    Product updateProduct(final ProductCreateDTO productDTO, final Long productId);
}
