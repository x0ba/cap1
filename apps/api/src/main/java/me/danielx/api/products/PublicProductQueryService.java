package me.danielx.api.products;

import java.util.Locale;
import me.danielx.api.products.domain.Product;
import me.danielx.api.products.domain.ProductStatus;
import me.danielx.api.products.persistence.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PublicProductQueryService {
  private final ProductRepository productRepository;

  public PublicProductQueryService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Page<Product> findActiveProducts(Pageable pageable) {
    return productRepository.findByStatus(ProductStatus.ACTIVE, pageable);
  }

  public Product findActiveProduct(String slug) {
    String normalizedSlug = slug.toLowerCase(Locale.ROOT);
    return productRepository
        .findBySlugAndStatus(normalizedSlug, ProductStatus.ACTIVE)
        .orElseThrow(ProductNotFoundException::new);
  }
}
