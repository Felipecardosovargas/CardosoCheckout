package cardoso.commerce.app.controller;

import cardoso.commerce.app.controller.request.BasketRequest;
import cardoso.commerce.app.controller.request.PaymentRequest;
import cardoso.commerce.app.entity.Basket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Basket Management", description = "APIs para gerenciamento de carrinhos de compras")
public interface BasketControllerSwagger {

    @Operation(
            summary = "Obter carrinho por ID",
            description = "Recupera um carrinho de compras específico pelo seu identificador único"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Carrinho encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = Basket.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Carrinho não encontrado"
            )
    })
    ResponseEntity<Basket> getBasketById(
            @Parameter(description = "ID do carrinho", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id
    );

    @Operation(
            summary = "Criar novo carrinho",
            description = "Cria um novo carrinho de compras com os itens especificados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Carrinho criado com sucesso",
                    content = @Content(schema = @Schema(implementation = Basket.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos"
            )
    })
    ResponseEntity<Basket> createBasket(
            @Parameter(description = "Dados para criação do carrinho")
            @Valid @RequestBody BasketRequest request
    );

    @Operation(
            summary = "Atualizar carrinho",
            description = "Atualiza os itens e informações de um carrinho existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Carrinho atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = Basket.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Carrinho não encontrado"
            )
    })
    ResponseEntity<Basket> updateBasket(
            @Parameter(description = "ID do carrinho", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id,
            @Parameter(description = "Dados para atualização do carrinho")
            @Valid @RequestBody BasketRequest request
    );

    @Operation(
            summary = "Definir método de pagamento",
            description = "Define ou atualiza o método de pagamento para um carrinho específico"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Método de pagamento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = Basket.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de pagamento inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Carrinho não encontrado"
            )
    })
    ResponseEntity<Basket> payBasket(
            @Parameter(description = "ID do carrinho", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id,
            @Parameter(description = "Dados do método de pagamento")
            @Valid @RequestBody PaymentRequest request
    );

    @Operation(
            summary = "Excluir carrinho",
            description = "Remove permanentemente um carrinho de compras"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Carrinho excluído com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Carrinho não encontrado"
            )
    })
    ResponseEntity<Void> deleteBasket(
            @Parameter(description = "ID do carrinho", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id
    );
}