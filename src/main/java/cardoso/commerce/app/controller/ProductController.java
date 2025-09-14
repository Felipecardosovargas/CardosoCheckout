package cardoso.commerce.app.controller;

import cardoso.commerce.app.client.response.PlatziProductResponse;
import cardoso.commerce.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController implements ProductControllerSwagger {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<PlatziProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatziProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}