package me.danielx.api.products.persistence;

import java.util.Optional;
import me.danielx.api.products.domain.Product;
import me.danielx.api.products.domain.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Page<Product> findByStatus(ProductStatus status, Pageable pageable);

  Optional<Product> findBySlugAndStatus(String slug, ProductStatus productStatus);
}
