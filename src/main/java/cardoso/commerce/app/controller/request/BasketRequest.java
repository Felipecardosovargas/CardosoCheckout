package cardoso.commerce.app.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Requisição para criação ou atualização de um carrinho de compras")
public record BasketRequest(

        @Schema(
                description = "Identificador único do cliente",
                example = "12345",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long clientId,

        @Schema(
                description = "Lista de produtos a serem adicionados ao carrinho",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        List<ProductRequest> products

) {}