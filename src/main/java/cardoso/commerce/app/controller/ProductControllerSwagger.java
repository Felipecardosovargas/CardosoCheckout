package cardoso.commerce.app.controller;

import cardoso.commerce.app.client.response.PlatziProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Product Management", description = "APIs para consulta de produtos")
public interface ProductControllerSwagger {

    @Operation(
            summary = "Obter todos os produtos",
            description = "Recupera uma lista de todos os produtos disponíveis"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de produtos recuperada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlatziProductResponse.class)))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor ao buscar produtos"
            )
    })
    ResponseEntity<List<PlatziProductResponse>> getAllProducts();

    @Operation(
            summary = "Obter produto por ID",
            description = "Recupera um produto específico pelo seu identificador único"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = PlatziProductResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor ao buscar o produto"
            )
    })
    ResponseEntity<PlatziProductResponse> getProductById(
            @Parameter(
                    description = "ID do produto",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    );
}