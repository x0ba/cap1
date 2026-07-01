package me.danielx.api.products;

import me.danielx.api.products.api.v1.PublicProductController;
import me.danielx.api.products.api.v1.dto.PublicProductDetailResponse;
import me.danielx.api.products.domain.CreditCardProduct;
import me.danielx.api.products.domain.CreditCardRewardCategory;
import me.danielx.api.products.domain.DepositProduct;
import me.danielx.api.products.domain.Product;
import me.danielx.api.products.domain.ProductStatus;
import me.danielx.api.products.domain.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

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
  void shouldReturnCreditCardProductWhenSlugMatches() throws Exception {
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

  @Test
  void shouldReturnDepositProductWhenSlugMatches() throws Exception {
    String slug = "test-savings-account";
    Product product =
        new DepositProduct(
            slug,
            "Test Savings Account",
            "Test Savings Account",
            "A test savings account with a competitive APY.",
            ProductType.SAVINGS,
            ProductStatus.ACTIVE,
            false,
            true,
            1,
            new BigDecimal("4.25000"),
            100L,
            5L,
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
        .andExpect(jsonPath("$.productDetails.*", hasSize(5)))
        .andExpect(jsonPath("$.productDetails.kind").value("DEPOSIT"))
        .andExpect(jsonPath("$.productDetails.apy").value(4.25000))
        .andExpect(jsonPath("$.productDetails.minimumDeposit").value(100))
        .andExpect(jsonPath("$.productDetails.monthlyFee").value(5))
        .andExpect(jsonPath("$.productDetails.minimumBalance").value(500));

    verify(productQueryService).findActiveProduct(slug);
  }

  @Test
  void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
    String slug = "non-existent-product";

    when(productQueryService.findActiveProduct(slug)).thenThrow(new ProductNotFoundException());

    mockMvc
        .perform(get("/api/public/v1/products/{slug}", slug))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.title").value("Request failed"))
        .andExpect(jsonPath("$.detail").value("Product not found"))
        .andExpect(jsonPath("$.instance").value("/api/public/v1/products/" + slug));

    verify(productQueryService).findActiveProduct(slug);
  }

  @Test
  void shouldReturnBadRequestWhenSlugIsInvalid() throws Exception {
    String slug = "invalid@slug";

    when(productQueryService.findActiveProduct(slug)).thenThrow(new IllegalArgumentException());

    mockMvc
        .perform(get("/api/public/v1/products/{slug}", slug))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Invalid request"))
        .andExpect(jsonPath("$.detail").value("Request validation failed"))
        .andExpect(jsonPath("$.instance").value("/api/public/v1/products/" + slug));
  }

  @Test
  void shouldReturnPageOf20SortedByDisplayOrderNameId() throws Exception {
    List<Product> productList =
        IntStream.range(0, 20)
            .mapToObj(
                i ->
                    new DepositProduct(
                        "test-product-" + i,
                        "Test Savings Account",
                        "Test Savings Account",
                        "A test savings account with a competitive APY.",
                        ProductType.SAVINGS,
                        ProductStatus.ACTIVE,
                        false,
                        true,
                        i,
                        new BigDecimal("4.25000"),
                        100L,
                        5L,
                        500L))
            .toList();
    Pageable pageable = PageRequest.of(0, 20);
    Page<Product> page = new PageImpl<>(productList, pageable, 20);

    when(productQueryService.findActiveProducts(pageable)).thenReturn(page);
  }
}
