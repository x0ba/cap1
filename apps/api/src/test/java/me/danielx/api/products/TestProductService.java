package me.danielx.api.products;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
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
    Product product =
        new Product(
            1L,
            slug,
            "Test Product",
            "Short description",
            "Description",
            ProductType.SAVINGS,
            ProductStatus.ACTIVE,
            false,
            true,
            0,
            Instant.now(),
            null,
            0);

    when(productRepository.findBySlugAndStatus(slug, ProductStatus.ACTIVE))
        .thenReturn(Optional.of(product));

    Product result = productService.findActiveProduct(slug);

    assertEquals(product, result);
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
    Product product1 =
        new Product(
            1L,
            "product1",
            "Product 1",
            "Description",
            "Short " + "description",
            ProductType.SAVINGS,
            ProductStatus.ACTIVE,
            false,
            true,
            0,
            Instant.now(),
            null,
            0);
    List<Product> productList = List.of(product1);
    Pageable pageable = PageRequest.of(0, 20);
    Page<Product> productPage = new PageImpl<>(productList, pageable, productList.size());

    when(productRepository.findByStatus(ProductStatus.ACTIVE, pageable)).thenReturn(productPage);

    Page<Product> result = productService.findActiveProducts(pageable);

    assertSame(productPage, result);
    verify(productRepository).findByStatus(ProductStatus.ACTIVE, pageable);
  }
}
