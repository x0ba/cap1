package me.danielx.api.products;

import me.danielx.api.products.api.v1.PublicProductController;
import me.danielx.api.products.api.v1.dto.PublicProductDetailResponse;
import me.danielx.api.products.domain.CreditCardProduct;
import me.danielx.api.products.domain.CreditCardRewardCategory;
import me.danielx.api.products.domain.Product;
import me.danielx.api.products.domain.ProductStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PublicProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PublicProductControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private PublicProductQueryService productQueryService;

  @Test
  void testGetProductGetsProductWithMatchingSlug() throws Exception {
    String slug = "test-credit-card";
    Product product =
        new CreditCardProduct(
            slug,
            "Test Credit Card",
            "Test Credit Card",
            "Test Credit Card",
            ProductStatus.ACTIVE,
            true,
            true,
            0,
            CreditCardRewardCategory.ALL_PURCHASES,
            new BigDecimal("0.03"),
            500L);
    PublicProductDetailResponse expectedResponse = PublicProductDetailResponse.from(product);

    when(productQueryService.findActiveProduct(slug)).thenReturn(product);

    mockMvc
        .perform(get("/api/public/v1/products/{slug}", slug))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(8)))
        .andExpect(jsonPath("$.slug").value(expectedResponse.slug()))
        .andExpect(jsonPath("$.name").value(expectedResponse.name()))
        .andExpect(jsonPath("$.shortDescription").value(expectedResponse.shortDescription()))
        .andExpect(jsonPath("$.description").value(expectedResponse.description()))
        .andExpect(jsonPath("$.type").value(expectedResponse.type().name()))
        .andExpect(jsonPath("$.featured").value(expectedResponse.featured()))
        .andExpect(
            jsonPath("$.applicationAvailable").value(expectedResponse.applicationAvailable()))
        .andExpect(jsonPath("$.productDetails.*", hasSize(4)))
        .andExpect(jsonPath("$.productDetails.kind").value("CREDIT_CARD"))
        .andExpect(
            jsonPath("$.productDetails.rewardCategory")
                .value(CreditCardRewardCategory.ALL_PURCHASES.name()))
        .andExpect(jsonPath("$.productDetails.rewardRate").value(0.03))
        .andExpect(jsonPath("$.productDetails.spendingCap").value(500));

    verify(productQueryService).findActiveProduct(slug);
  }
}
