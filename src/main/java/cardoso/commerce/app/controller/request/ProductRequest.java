package cardoso.commerce.app.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Requisição para adicionar um produto ao carrinho")
public record ProductRequest(

        @Schema(
                description = "ID do produto a ser adicionado ao carrinho",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "ID do produto é obrigatório")
        Long productId,

        @Schema(
                description = "Quantidade do produto a ser adicionada",
                example = "2",
                defaultValue = "1",
                minimum = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Quantidade é obrigatória")
        Integer quantity

) {}