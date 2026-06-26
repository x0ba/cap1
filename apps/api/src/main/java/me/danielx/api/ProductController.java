package me.danielx.api;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public Product getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public void addNewProduct(@RequestBody Product product) {
        productService.insertProduct(product);
    }

}
