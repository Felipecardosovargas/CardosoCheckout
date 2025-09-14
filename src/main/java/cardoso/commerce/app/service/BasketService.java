package cardoso.commerce.app.service;

import cardoso.commerce.app.client.response.PlatziProductResponse;
import cardoso.commerce.app.controller.request.BasketRequest;
import cardoso.commerce.app.controller.request.PaymentRequest;
import cardoso.commerce.app.controller.request.ProductRequest;
import cardoso.commerce.app.entity.Basket;
import cardoso.commerce.app.entity.Product;
import cardoso.commerce.app.entity.Status;
import cardoso.commerce.app.exception.BusinessesException;
import cardoso.commerce.app.exception.DataNotFoundException;
import cardoso.commerce.app.repository.BasketRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;

    public Basket createBasket(BasketRequest basketRequest) {
        Optional<Basket> existingBasketOpt = basketRepository.findByClientIdAndStatus(basketRequest.clientId(), Status.OPEN);

        if (existingBasketOpt.isPresent()) {
            Basket existingBasket = existingBasketOpt.get();
            addProductsToBasket(existingBasket, basketRequest.products());
            existingBasket.calculateTotalPrice();
            return basketRepository.save(existingBasket);
        }

        List<Product> products = createProductList(basketRequest.products());

        Basket newBasket = Basket.builder()
                .clientId(basketRequest.clientId())
                .status(Status.OPEN)
                .products(products)
                .build();

        newBasket.calculateTotalPrice();
        return basketRepository.save(newBasket);
    }

    private List<Product> createProductList(List<ProductRequest> productRequests) {
        List<Product> products = new ArrayList<>();
        productRequests.forEach(productRequest -> {
            PlatziProductResponse platziProductResponse = productService.getProductById(productRequest.productId());

            products.add(Product.builder()
                    .id(platziProductResponse.id())
                    .title(platziProductResponse.title())
                    .price(platziProductResponse.price())
                    .quantity(productRequest.quantity())
                    .build());
        });
        return products;
    }

    private void addProductsToBasket(Basket basket, List<ProductRequest> productRequests) {
        productRequests.forEach(productRequest -> {
            PlatziProductResponse platziProductResponse = productService.getProductById(productRequest.productId());

            Product newProduct = Product.builder()
                    .id(platziProductResponse.id())
                    .title(platziProductResponse.title())
                    .price(platziProductResponse.price())
                    .quantity(productRequest.quantity())
                    .build();

            basket.getProducts().add(newProduct);
        });
    }

    public Basket getBasketById(String id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Basket not found with id: " + id));
    }

    public Basket updateBasket(String id, @Valid BasketRequest request) {
        Basket basket = getBasketById(id);

        if (basket.getStatus() != Status.OPEN) {
            throw new BusinessesException("Cannot update a closed basket");
        }

        List<Product> products = new ArrayList<>();
        request.products().forEach(product -> {
            PlatziProductResponse platziProductResponse = productService.getProductById(product.productId());

            products.add(Product.builder()
                    .id(platziProductResponse.id())
                    .title(platziProductResponse.title())
                    .price(platziProductResponse.price())
                    .quantity(product.quantity())
                    .build());
        });

        basket.setProducts(products);
        basket.calculateTotalPrice();

        return basketRepository.save(basket);
    }

    public Basket updatePaymentMethod(String id, @Valid PaymentRequest request) {
        Basket basket = getBasketById(id);
        basket.setPaymentMethod(request.getPaymentMethod());
        basket.setStatus(Status.SOLD);
        return basketRepository.save(basket);
    }

    public void deleteBasket(String id) {
        basketRepository.deleteById(id);
    }
}