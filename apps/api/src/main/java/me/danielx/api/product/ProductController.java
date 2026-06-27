package me.danielx.api.product;

import me.danielx.api.product.dto.PublicProductResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<PublicProductResponse> getProducts() {
        return productService.getPublicProducts().stream().map(PublicProductResponse::from).toList();
    }

    @GetMapping("{id}")
    public PublicProductResponse getProductById(@PathVariable Long id) {
        return PublicProductResponse.from(productService.getPublicProductById(id));
    }

    @PostMapping
    public void addNewProduct(@RequestBody Product product) {
        productService.insertProduct(product);
    }

}
