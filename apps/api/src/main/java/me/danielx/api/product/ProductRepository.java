package me.danielx.api.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusOrderByDisplayOrderAscNameAsc(ProductStatus status);
    Optional<Product> findByIdAndStatus(Long id, ProductStatus status);
}
