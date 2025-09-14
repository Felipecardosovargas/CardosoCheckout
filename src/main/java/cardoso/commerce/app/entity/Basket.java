package cardoso.commerce.app.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "basket")
@Schema(description = "Entidade que representa um carrinho de compras")
public class Basket {

    @Id
    @Schema(
            description = "Identificador único do carrinho (UUID)",
            example = "123e4567-e89b-12d3-a456-426614174000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String id;

    @Schema(
            description = "ID do cliente proprietário do carrinho",
            example = "12345",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long clientId;

    @Schema(
            description = "Preço total do carrinho (calculado automaticamente)",
            example = "299.99",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private BigDecimal totalPrice;

    @Schema(
            description = "Lista de produtos no carrinho",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<Product> products;

    @Schema(
            description = "Status atual do carrinho",
            example = "ACTIVE",
            defaultValue = "ACTIVE"
    )
    private Status status;

    @Schema(
            description = "Método de pagamento associado ao carrinho (opcional)",
            nullable = true
    )
    private PaymentMethod paymentMethod;

    public void calculateTotalPrice() {
        this.totalPrice = products.stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}