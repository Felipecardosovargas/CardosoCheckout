package cardoso.commerce.app.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Schema(description = "Representa um produto dentro de um carrinho de compras")
public class Product {

    @Schema(
            description = "Identificador único do produto",
            example = "123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;

    @Schema(
            description = "Nome/título do produto",
            example = "Smartphone Premium XYZ",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @Schema(
            description = "Preço unitário do produto",
            example = "599.99",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigDecimal price;

    @Schema(
            description = "Quantidade do produto no carrinho",
            example = "2",
            defaultValue = "1",
            minimum = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer quantity;
}