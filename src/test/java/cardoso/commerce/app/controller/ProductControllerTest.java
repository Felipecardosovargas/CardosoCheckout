package cardoso.commerce.app.controller;

import cardoso.commerce.app.client.response.PlatziProductResponse;
import cardoso.commerce.app.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product Controller Unit Tests")
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private PlatziProductResponse mockProduct;
    private List<PlatziProductResponse> mockProducts;

    @BeforeEach
    void setUp() {
        mockProduct = PlatziProductResponse.builder()
                .id(1L)
                .title("Test Product")
                .price(BigDecimal.valueOf(99.99))
                .build();

        mockProducts = List.of(
                mockProduct,
                PlatziProductResponse.builder()
                        .id(2L)
                        .title("Another Product")
                        .price(BigDecimal.valueOf(49.99))
                        .build()
        );
    }

    @Test
    @DisplayName("Should return all products successfully")
    void getAllProducts_ShouldReturnAllProducts() {
        // Arrange
        when(productService.getAllProducts()).thenReturn(mockProducts);

        // Act
        ResponseEntity<List<PlatziProductResponse>> response = productController.getAllProducts();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(mockProducts, response.getBody());

        verify(productService, times(1)).getAllProducts();
        verifyNoMoreInteractions(productService);
    }

    @Test
    @DisplayName("Should return empty list when no products exist")
    void getAllProducts_ShouldReturnEmptyList_WhenNoProductsExist() {
        // Arrange
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<PlatziProductResponse>> response = productController.getAllProducts();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(productService, times(1)).getAllProducts();
        verifyNoMoreInteractions(productService);
    }

    @Test
    @DisplayName("Should return product by ID successfully")
    void getProductById_ShouldReturnProduct_WhenValidId() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(mockProduct);

        // Act
        ResponseEntity<PlatziProductResponse> response = productController.getProductById(productId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockProduct, response.getBody());
        assertEquals(productId, response.getBody().id());

        verify(productService, times(1)).getProductById(productId);
        verifyNoMoreInteractions(productService);
    }

    @Test
    @DisplayName("Should return product when service returns product for different ID")
    void getProductById_ShouldReturnProduct_ForDifferentId() {
        // Arrange
        Long productId = 2L;
        PlatziProductResponse anotherProduct = PlatziProductResponse.builder()
                .id(2L)
                .title("Another Product")
                .price(BigDecimal.valueOf(149.99))
                .build();

        when(productService.getProductById(productId)).thenReturn(anotherProduct);

        // Act
        ResponseEntity<PlatziProductResponse> response = productController.getProductById(productId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(anotherProduct, response.getBody());
        assertEquals(productId, response.getBody().id());

        verify(productService, times(1)).getProductById(productId);
        verifyNoMoreInteractions(productService);
    }

    @Test
    @DisplayName("Should handle service method calls with correct parameters")
    void getProductById_ShouldCallServiceWithCorrectParameter() {
        // Arrange
        Long productId = 5L;
        when(productService.getProductById(productId)).thenReturn(mockProduct);

        // Act
        ResponseEntity<PlatziProductResponse> response = productController.getProductById(productId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Only this verification is needed
        verify(productService).getProductById(productId);

        // Optional: make sure no other interactions happened
        verifyNoMoreInteractions(productService);
    }
}