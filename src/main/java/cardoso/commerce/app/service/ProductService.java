package cardoso.commerce.app.service;

import cardoso.commerce.app.client.PlatziStoreClient;
import cardoso.commerce.app.client.response.PlatziProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final PlatziStoreClient platziStoreClient;

    private static final String PRODUCT_CACHE = "products";

    @Cacheable(value = PRODUCT_CACHE)
    public List<PlatziProductResponse> getAllProducts() {
        return platziStoreClient.getAllProducts();
    }

    @Cacheable(value = PRODUCT_CACHE, key = "#id")
    public PlatziProductResponse getProductById(long id) {
        return platziStoreClient.getProductById(id);
    }

    @Cacheable(value = PRODUCT_CACHE, key = "#id", unless = "#result == null")
    public PlatziProductResponse getProductByIdWithNullCheck(long id) {
        PlatziProductResponse product = platziStoreClient.getProductById(id);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return product;
    }
}