package me.danielx.api.product;

import me.danielx.api.product.dto.PageResponse;
import me.danielx.api.product.dto.PublicProductResponse;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public PageResponse<PublicProductResponse> getProducts(
            @PageableDefault(
                    size = 20,
                    sort = {"displayOrder", "name", "id"},
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        return PageResponse.from(productService.getPublicProducts(pageable).map(PublicProductResponse::from));
    }

    @GetMapping("/{id}")
    public PublicProductResponse getProductById(@PathVariable Long id) {
        return PublicProductResponse.from(productService.getPublicProductById(id));
    }

    @PostMapping
    public void addNewProduct(@RequestBody Product product) {
        productService.insertProduct(product);
    }

}
