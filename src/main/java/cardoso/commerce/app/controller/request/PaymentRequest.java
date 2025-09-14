package cardoso.commerce.app.controller.request;

import cardoso.commerce.app.entity.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Requisição para processamento de pagamento de um carrinho")
public class PaymentRequest {

    @Schema(
            description = "Método de pagamento a ser utilizado",
            requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = PaymentMethod.class
    )
    private PaymentMethod paymentMethod;
}