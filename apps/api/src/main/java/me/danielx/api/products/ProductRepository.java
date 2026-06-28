package me.danielx.api.products;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Page<Product> findByStatus(ProductStatus status, Pageable pageable);

  Optional<Product> findBySlugAndStatus(String slug, ProductStatus productStatus);
}
