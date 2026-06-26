package me.danielx.api;

import me.danielx.api.dto.PublicProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getPublicProducts() {
        return productRepository.findByStatusOrderByDisplayOrderAscNameAsc(ProductStatus.ACTIVE);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void insertProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + "not found"));
    }
}