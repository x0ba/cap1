package me.danielx.api.product;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public Product getPublicProductById(Long id) {
        return productRepository.findByIdAndStatus(id, ProductStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
}