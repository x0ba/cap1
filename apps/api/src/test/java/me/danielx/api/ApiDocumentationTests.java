package me.danielx.api;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import me.danielx.api.products.application.ProductNotFoundException;
import me.danielx.api.products.application.PublicProductQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
    properties =
        "spring.autoconfigure.exclude="
            + "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration,"
            + "org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration,"
            + "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration,"
            + "org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration,"
            + "org.springframework.boot.flyway.autoconfigure.FlywayAutoConfiguration,"
            + "org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration,"
            + "org.springframework.boot.data.redis.autoconfigure.DataRedisRepositoriesAutoConfiguration,"
            + "org.springframework.boot.session.data.redis.autoconfigure.SessionDataRedisAutoConfiguration")
@AutoConfigureMockMvc
class ApiDocumentationTests {

  @Autowired MockMvc mockMvc;

  @MockitoBean PublicProductQueryService productQueryService;

  @Test
  void openApiContractDocumentsPublicProductEndpoints() throws Exception {
    mockMvc
        .perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.openapi").value("3.1.0"))
        .andExpect(jsonPath("$.info.title").value("Capital Two API"))
        .andExpect(jsonPath("$.info.version").value("v1"))
        .andExpect(
            jsonPath("$['paths']['/api/public/v1/products']['get']['operationId']")
                .value("listPublicProducts"))
        .andExpect(
            jsonPath("$['paths']['/api/public/v1/products']['get']['parameters'][*].name")
                .value(containsInAnyOrder("page", "size", "sort")))
        .andExpect(
            jsonPath(
                    "$['paths']['/api/public/v1/products']['get']['responses']['200']"
                        + ".content['application/json'].schema['$ref']")
                .value("#/components/schemas/PageResponsePublicProduct"))
        .andExpect(
            jsonPath(
                    "$['paths']['/api/public/v1/products']['get']['responses']['400']"
                        + ".content['application/problem+json'].schema['$ref']")
                .value("#/components/schemas/ApiProblem"))
        .andExpect(
            jsonPath(
                    "$['paths']['/api/public/v1/products/{slug}']['get']['responses']['404']"
                        + ".content['application/problem+json'].schema['$ref']")
                .value("#/components/schemas/ApiProblem"))
        .andExpect(
            jsonPath("$.components.schemas.PublicProduct.required[*]")
                .value(
                    containsInAnyOrder(
                        "id", "slug", "name", "type", "featured", "applicationAvailable")))
        .andExpect(
            jsonPath("$.components.schemas.ApiProblem.required[*]")
                .value(containsInAnyOrder("title", "status", "detail", "instance", "timestamp")));
  }

  @Test
  void openApiContractDocumentsPolymorphicProductDetails() throws Exception {
    mockMvc
        .perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath(
                    "$.components.schemas.PublicProductDetailResponse.properties"
                        + ".productDetails.oneOf[*]['$ref']")
                .value(
                    containsInAnyOrder(
                        "#/components/schemas/DepositProductDetails",
                        "#/components/schemas/CreditCardProductDetails")))
        .andExpect(
            jsonPath("$.components.schemas.ProductDetails.discriminator.propertyName")
                .value("kind"))
        .andExpect(
            jsonPath("$.components.schemas.ProductDetails.discriminator.mapping.DEPOSIT")
                .value("#/components/schemas/DepositProductDetails"))
        .andExpect(
            jsonPath("$.components.schemas.ProductDetails.discriminator.mapping.CREDIT_CARD")
                .value("#/components/schemas/CreditCardProductDetails"))
        .andExpect(
            jsonPath("$.components.schemas.DepositProductDetails.required[*]")
                .value(
                    containsInAnyOrder(
                        "kind", "apy", "minimumDeposit", "minimumBalance", "monthlyFee")))
        .andExpect(
            jsonPath("$.components.schemas.CreditCardProductDetails.required[*]")
                .value(
                    containsInAnyOrder(
                        "kind", "rewardCategory", "rewardRate", "spendingCap")))
        .andExpect(
            jsonPath(
                    "$.components.schemas.DepositProductDetails.allOf[1]"
                        + ".properties.kind.enum[0]")
                .value("DEPOSIT"))
        .andExpect(
            jsonPath(
                    "$.components.schemas.CreditCardProductDetails.allOf[1]"
                        + ".properties.kind.enum[0]")
                .value("CREDIT_CARD"));
  }

  @Test
  void invalidSlugMatchesDocumentedProblemResponse() throws Exception {
    mockMvc
        .perform(get("/api/public/v1/products/INVALID"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
        .andExpect(jsonPath("$.title").value("Invalid request"))
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.detail").value("Request validation failed"))
        .andExpect(jsonPath("$.instance").value("/api/public/v1/products/INVALID"))
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  void missingProductMatchesDocumentedProblemResponse() throws Exception {
    when(productQueryService.findActiveProduct("missing-product"))
        .thenThrow(new ProductNotFoundException());

    mockMvc
        .perform(get("/api/public/v1/products/missing-product"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
        .andExpect(jsonPath("$.title").value("Request failed"))
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.detail").value("Product not found"))
        .andExpect(jsonPath("$.instance").value("/api/public/v1/products/missing-product"))
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }
}
