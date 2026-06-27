package me.danielx.api.product.api;

import me.danielx.api.product.ProductService;
import me.danielx.api.common.dto.PageResponse;
import me.danielx.api.product.dto.PublicProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/v1/products")
public class PublicProductController {
    private final ProductService productService;

    public PublicProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public PageResponse<PublicProductResponse> listProducts(
            @PageableDefault(
                    size = 20,
                    sort = {"displayOrder", "name", "id"},
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        return PageResponse.from(productService.findActiveProducts(pageable).map(PublicProductResponse::from));
    }

    @GetMapping("/{slug}")
    public PublicProductResponse getProduct(@PathVariable String slug) {
        return PublicProductResponse.from(productService.findActiveProduct(slug));
    }
}
