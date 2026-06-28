package me.danielx.api.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestProductService {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void findActiveProductReturnsMatchingActiveProduct() {
        String slug = "test-product";
        Product product = new Product(
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
            0
        );

        when(productRepository.findBySlugAndStatus(slug, ProductStatus.ACTIVE)).thenReturn(Optional.of(product));

        Product result = productService.findActiveProduct(slug);

        assertEquals(product, result);
        verify(productRepository).findBySlugAndStatus(slug, ProductStatus.ACTIVE);
    }

    @Test
    void findActiveProductThrowsExceptionWhenProductNotFound() {
        String slug = "non-existent-product";

        when(productRepository.findBySlugAndStatus(slug, ProductStatus.ACTIVE)).thenReturn(Optional.empty());

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> productService.findActiveProduct(slug));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Product not found", exception.getReason());

        verify(productRepository).findBySlugAndStatus(slug, ProductStatus.ACTIVE);
    }

}
