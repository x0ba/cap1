package me.danielx.api.products;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class TestProductService {

  @Mock ProductRepository productRepository;

  @InjectMocks ProductService productService;

  @Test
  void findActiveProductReturnsMatchingActiveProduct() {
    String slug = "test-product";
    Product product = depositProduct(slug);

    when(productRepository.findBySlugAndStatus(slug, ProductStatus.ACTIVE))
        .thenReturn(Optional.of(product));

    Product result = productService.findActiveProduct(slug);

    assertSame(product, result);
    verify(productRepository).findBySlugAndStatus(slug, ProductStatus.ACTIVE);
  }

  @Test
  void findActiveProductThrowsExceptionWhenProductNotFound() {
    String slug = "non-existent-product";

    when(productRepository.findBySlugAndStatus(slug, ProductStatus.ACTIVE))
        .thenReturn(Optional.empty());

    ResponseStatusException exception =
        assertThrows(ResponseStatusException.class, () -> productService.findActiveProduct(slug));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Product not found", exception.getReason());

    verify(productRepository).findBySlugAndStatus(slug, ProductStatus.ACTIVE);
  }

  @Test
  void findActiveProductsReturnsPageOfActiveProducts() {
    Product product =
        new CreditCardProduct(
            "cash-back-card",
            "Cash Back Card",
            "Earn cash back on every purchase",
            "A credit card with unlimited cash-back rewards.",
            ProductStatus.ACTIVE,
            true,
            true,
            1,
            CreditCardRewardCategory.ALL_PURCHASES,
            new BigDecimal("1.500"),
            0L);

    List<Product> productList = List.of(product);
    Pageable pageable = PageRequest.of(0, 20);
    Page<Product> productPage = new PageImpl<>(productList, pageable, productList.size());

    when(productRepository.findByStatus(ProductStatus.ACTIVE, pageable)).thenReturn(productPage);

    Page<Product> result = productService.findActiveProducts(pageable);

    assertSame(productPage, result);
    verify(productRepository).findByStatus(ProductStatus.ACTIVE, pageable);
  }

  private static DepositProduct depositProduct(String slug) {
    return new DepositProduct(
        slug,
        "Test Savings Account",
        "A savings account",
        "A savings account used by the product service test.",
        ProductType.SAVINGS,
        ProductStatus.ACTIVE,
        false,
        true,
        0,
        new BigDecimal("4.25000"),
        100L,
        0L,
        500L);
  }
}
