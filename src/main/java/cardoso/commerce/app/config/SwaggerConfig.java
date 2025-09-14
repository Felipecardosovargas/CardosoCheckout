package cardoso.commerce.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Basket Service API",
                version = "1.0.0",
                description = """
            Microsserviço para gerenciamento de carrinhos de compras e integração com API de produtos.
            
            ## Funcionalidades:
            - 🛒 Gerenciamento de carrinhos de compras
            - 📦 Integração com API externa de produtos (Platzi Fake Store)
            - 💳 Processamento de pagamentos
            - 🔄 Operações CRUD completas
            - ⚡ Cache com Redis para melhor performance
            
            ## Bancos de Dados:
            - **MongoDB**: Armazenamento de carrinhos
            - **Redis**: Cache para melhor performance
            
            ## API Externa:
            - **Platzi Fake Store**: https://api.escuelajs.co/api/v1
            """,
                contact = @Contact(
                        name = "Felipe Cardoso Vargas",
                        email = "felipecardosovargas1@gmail.com",
                        url = ""
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "https://www.commerce.com/terms"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Servidor Local de Desenvolvimento"
                ),
                @Server(
                        url = "https://dev.commerce.com",
                        description = "Servidor de Desenvolvimento"
                ),
                @Server(
                        url = "https://staging.commerce.com",
                        description = "Servidor de Staging"
                ),
                @Server(
                        url = "https://api.commerce.com",
                        description = "Servidor de Produção"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "Autenticação JWT. Use: Bearer <token>"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Basket Service API")
                        .version("1.0.0")
                        .description("API completa para gerenciamento de carrinhos de compras")
                        .termsOfService("https://www.commerce.com/terms")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Suporte Commerce")
                                .email("suporte@commerce.com")
                                .url("https://www.commerce.com/support"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem ->
                    pathItem.readOperations().forEach(operation -> {
                        ApiResponses responses = operation.getResponses();

                        if (!responses.containsKey("400")) {
                            responses.addApiResponse("400", createApiResponse(
                                    "Dados de entrada inválidos",
                                    "Erro de validação nos dados enviados"
                            ));
                        }

                        if (!responses.containsKey("401")) {
                            responses.addApiResponse("401", createApiResponse(
                                    "Não autorizado",
                                    "Token JWT inválido ou expirado"
                            ));
                        }

                        if (!responses.containsKey("403")) {
                            responses.addApiResponse("403", createApiResponse(
                                    "Acesso proibido",
                                    "Usuário não tem permissão para acessar este recurso"
                            ));
                        }

                        if (!responses.containsKey("404")) {
                            responses.addApiResponse("404", createApiResponse(
                                    "Recurso não encontrado",
                                    "O recurso solicitado não foi encontrado"
                            ));
                        }

                        if (!responses.containsKey("500")) {
                            responses.addApiResponse("500", createApiResponse(
                                    "Erro interno do servidor",
                                    "Ocorreu um erro inesperado no servidor"
                            ));
                        }
                    })
            );

            addGlobalExamples(openApi);
        };
    }

    private ApiResponse createApiResponse(String description, String exampleDescription) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType(
                        org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                        new MediaType().example(exampleDescription)
                ));
    }

    private void addGlobalExamples(OpenAPI openApi) {
        Components components = openApi.getComponents();

        Example validationErrorExample = new Example()
                .summary("Erro de validação")
                .description("Exemplo de resposta quando os dados de entrada são inválidos")
                .value("""
                {
                  "timestamp": "2023-12-01T10:00:00.000Z",
                  "status": 400,
                  "error": "Bad Request",
                  "message": "Erro de validação",
                  "path": "/api/basket",
                  "details": [
                    {
                      "field": "clientId",
                      "message": "ID do cliente é obrigatório"
                    },
                    {
                      "field": "products",
                      "message": "Lista de produtos não pode estar vazia"
                    }
                  ]
                }
                """);

        Example internalErrorExample = new Example()
                .summary("Erro interno")
                .description("Exemplo de resposta quando ocorre um erro interno no servidor")
                .value("""
                {
                  "timestamp": "2023-12-01T10:00:00.000Z",
                  "status": 500,
                  "error": "Internal Server Error",
                  "message": "Ocorreu um erro inesperado",
                  "path": "/api/basket"
                }
                """);

        components.addExamples("ValidationErrorExample", validationErrorExample);
        components.addExamples("InternalErrorExample", internalErrorExample);
    }

    @Bean
    public OpenApiCustomizer operationCustomizer() {
        return openApi -> {
            openApi.getPaths().forEach((path, pathItem) -> {
                pathItem.readOperations().forEach(operation -> {
                    if (path.contains("basket")) {
                        operation.addTagsItem("Basket Management");
                    } else if (path.contains("products")) {
                        operation.addTagsItem("Product Management");
                    } else if (path.contains("payment")) {
                        operation.addTagsItem("Payment Processing");
                    }

                    if (operation.getSummary() == null) {
                        String method = operation.getClass().getSimpleName();
                        switch (method) {
                            case "GetOperation" -> operation.setSummary("Consultar recurso");
                            case "PostOperation" -> operation.setSummary("Criar recurso");
                            case "PutOperation" -> operation.setSummary("Atualizar recurso");
                            case "DeleteOperation" -> operation.setSummary("Excluir recurso");
                        }
                    }
                });
            });
        };
    }
}