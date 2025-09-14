package cardoso.commerce.app.client.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "Resposta da API externa Platzi Fake Store para produtos")
@Builder
public record PlatziProductResponse(

        @Schema(
                description = "Identificador único do produto na API Platzi",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long id,

        @Schema(
                description = "Título/nome do produto",
                example = "Smartphone Premium XYZ",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String title,

        @Schema(
                description = "Preço do produto",
                example = "599.99",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        BigDecimal price

) implements Serializable {}