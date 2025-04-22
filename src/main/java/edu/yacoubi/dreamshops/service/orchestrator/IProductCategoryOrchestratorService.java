package edu.yacoubi.dreamshops.service.orchestrator;

import edu.yacoubi.dreamshops.dto.product.ProductRequestDTO;
import edu.yacoubi.dreamshops.model.Product;

public interface IProductCategoryOrchestratorService {
    Product createProductForCategory(ProductRequestDTO productDTO, Long categoryId);
}
