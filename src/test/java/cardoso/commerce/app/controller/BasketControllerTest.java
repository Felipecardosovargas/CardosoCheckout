package cardoso.commerce.app.controller;

import cardoso.commerce.app.controller.request.BasketRequest;
import cardoso.commerce.app.controller.request.PaymentRequest;
import cardoso.commerce.app.controller.request.ProductRequest;
import cardoso.commerce.app.entity.Basket;
import cardoso.commerce.app.service.BasketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketControllerTest {

    @Mock
    private BasketService basketService;

    @InjectMocks
    private BasketController basketController;

    @Test
    void getBasketById_ShouldReturnBasket_WhenExists() {
        // Arrange
        String basketId = "basket-1";
        Basket expectedBasket = new Basket();
        when(basketService.getBasketById(basketId)).thenReturn(expectedBasket);

        // Act
        ResponseEntity<Basket> response = basketController.getBasketById(basketId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(expectedBasket, response.getBody());
        verify(basketService).getBasketById(basketId);
    }

    @Test
    void getBasketById_ShouldThrowException_WhenServiceThrowsException() {
        // Arrange
        String basketId = "invalid-id";
        when(basketService.getBasketById(basketId)).thenThrow(new RuntimeException("Not Found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> basketController.getBasketById(basketId));
        verify(basketService).getBasketById(basketId);
    }

    @Test
    void createBasket_ShouldReturnCreatedBasket_WhenRequestIsValid() {
        // Arrange
        List<ProductRequest> products = List.of(
                new ProductRequest(1L, 2)
        );
        BasketRequest request = new BasketRequest(12345L, products);

        Basket createdBasket = new Basket();
        when(basketService.createBasket(request)).thenReturn(createdBasket);

        // Act
        ResponseEntity<Basket> response = basketController.createBasket(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertSame(createdBasket, response.getBody());
        verify(basketService).createBasket(request);
    }

    @Test
    void createBasket_ShouldThrowValidationException_WhenRequestIsInvalid() {
        // Arrange
        List<ProductRequest> products = List.of(
                new ProductRequest(1L, 2)
        );
        BasketRequest invalidRequest = new BasketRequest(null, products);

        when(basketService.createBasket(invalidRequest))
                .thenThrow(new ConstraintViolationException("Validation failed", null));

        // Act & Assert
        assertThrows(ConstraintViolationException.class,
                () -> basketController.createBasket(invalidRequest));
        verify(basketService).createBasket(invalidRequest);
    }

    @Test
    void updateBasket_ShouldReturnUpdatedBasket_WhenRequestIsValid() {
        // Arrange
        String basketId = "basket-1";
        List<ProductRequest> products = List.of(
                new ProductRequest(1L, 2));
        BasketRequest request = new BasketRequest(12345L, products);

        Basket updatedBasket = new Basket();
        when(basketService.updateBasket(basketId, request)).thenReturn(updatedBasket);

        // Act
        ResponseEntity<Basket> response = basketController.updateBasket(basketId, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(updatedBasket, response.getBody());
        verify(basketService).updateBasket(basketId, request);
    }

    @Test
    void updateBasket_ShouldReturnNotFound_WhenBasketDoesNotExist() {
        // Arrange
        String nonExistentId = "non-existent";
        List<ProductRequest> products = List.of(
                new ProductRequest(1L, 2)
        );
        BasketRequest request = new BasketRequest(12345L, products);

        when(basketService.updateBasket(nonExistentId, request))
                .thenThrow(new RuntimeException("Basket not found"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> basketController.updateBasket(nonExistentId, request));
        verify(basketService).updateBasket(nonExistentId, request);
    }

    @Test
    void payBasket_ShouldReturnBasketWithPayment_WhenRequestIsValid() {
        // Arrange
        String basketId = "basket-1";
        PaymentRequest request = mock(PaymentRequest.class);
        Basket paidBasket = new Basket();
        when(basketService.updatePaymentMethod(basketId, request)).thenReturn(paidBasket);

        // Act
        ResponseEntity<Basket> response = basketController.payBasket(basketId, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(paidBasket, response.getBody());
        verify(basketService).updatePaymentMethod(basketId, request);
    }

    @Test
    void payBasket_ShouldThrowException_WhenPaymentFails() {
        // Arrange
        String basketId = "basket-1";
        PaymentRequest request = mock(PaymentRequest.class);
        when(basketService.updatePaymentMethod(basketId, request))
                .thenThrow(new RuntimeException("Payment failed"));

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> basketController.payBasket(basketId, request));
        verify(basketService).updatePaymentMethod(basketId, request);
    }

    @Test
    void deleteBasket_ShouldReturnNoContent_WhenBasketExists() {
        // Arrange
        String basketId = "basket-1";
        doNothing().when(basketService).deleteBasket(basketId);

        // Act
        ResponseEntity<Void> response = basketController.deleteBasket(basketId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(basketService).deleteBasket(basketId);
    }

    @Test
    void deleteBasket_ShouldThrowException_WhenBasketNotFound() {
        // Arrange
        String basketId = "non-existent";
        doThrow(new RuntimeException("Basket not found"))
                .when(basketService).deleteBasket(basketId);

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> basketController.deleteBasket(basketId));
        verify(basketService).deleteBasket(basketId);
    }
}