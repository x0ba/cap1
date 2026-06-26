package me.danielx.api;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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