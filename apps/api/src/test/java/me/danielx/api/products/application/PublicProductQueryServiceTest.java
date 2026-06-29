package me.danielx.api.products.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import me.danielx.api.products.domain.CreditCardProduct;
import me.danielx.api.products.domain.CreditCardRewardCategory;
import me.danielx.api.products.domain.DepositProduct;
import me.danielx.api.products.domain.Product;
import me.danielx.api.products.domain.ProductStatus;
import me.danielx.api.products.domain.ProductType;
import me.danielx.api.products.persistence.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class PublicProductQueryServiceTest {

  @Mock ProductRepository productRepository;

  @InjectMocks PublicProductQueryService productQueryService;

  @Test
  void findActiveProductReturnsMatchingActiveProduct() {
    String slug = "test-product";
    Product product = depositProduct(slug);

    when(productRepository.findBySlugAndStatus(slug, ProductStatus.ACTIVE))
        .thenReturn(Optional.of(product));

    Product result = productQueryService.findActiveProduct(slug);

    assertSame(product, result);
    verify(productRepository).findBySlugAndStatus(slug, ProductStatus.ACTIVE);
  }

  @Test
  void findActiveProductThrowsExceptionWhenProductNotFound() {
    String slug = "non-existent-product";

    when(productRepository.findBySlugAndStatus(slug, ProductStatus.ACTIVE))
        .thenReturn(Optional.empty());

    ProductNotFoundException exception =
        assertThrows(
            ProductNotFoundException.class, () -> productQueryService.findActiveProduct(slug));

    assertEquals("Product not found", exception.getMessage());
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

    Page<Product> result = productQueryService.findActiveProducts(pageable);

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
