package cardoso.commerce.app.controller;

import cardoso.commerce.app.controller.request.BasketRequest;
import cardoso.commerce.app.controller.request.PaymentRequest;
import cardoso.commerce.app.entity.Basket;
import cardoso.commerce.app.service.BasketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController implements BasketControllerSwagger{

    private final BasketService basketService;

    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable String id) {
        return ResponseEntity.ok(basketService.getBasketById(id));
    }

    @PostMapping
    public ResponseEntity<Basket> createBasket(@Valid @RequestBody BasketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(basketService.createBasket(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Basket> updateBasket(@PathVariable String id, @Valid @RequestBody BasketRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(basketService.updateBasket(id, request));
    }

    @PutMapping("/{id}/payment")
    public ResponseEntity<Basket> payBasket(@PathVariable String id, @Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(basketService.updatePaymentMethod(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable String id) {
        basketService.deleteBasket(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
