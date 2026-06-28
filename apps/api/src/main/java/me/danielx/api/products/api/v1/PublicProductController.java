package me.danielx.api.products.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import me.danielx.api.common.dto.PageResponse;
import me.danielx.api.products.ProductService;
import me.danielx.api.products.dto.PublicProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/api/public/v1/products")
public class PublicProductController {
  private final ProductService productService;
  private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("displayOrder", "name", "id");

  private void validateSort(Pageable pageable) {
    for (Sort.Order order : pageable.getSort()) {
      if (!ALLOWED_SORT_FIELDS.contains(order.getProperty())) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Unsupported sort field. Supported sort fields: " + ALLOWED_SORT_FIELDS);
      }
    }
  }

  public PublicProductController(ProductService productService) {
    this.productService = productService;
  }

  @Operation(
      summary = "List public products",
      description =
          "Returns a paginated list of active products that are visible to public users. "
              + "Products are sorted by display order, name, and ID by default.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Products returned successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid pagination or sort parameter")
  })
  @GetMapping
  public PageResponse<PublicProductResponse> listProducts(
      @Parameter(
              description =
                  "Pagination and sorting options. Defaults to size 20 sorted by displayOrder, name, and id ascending.")
          @PageableDefault(
              size = 20,
              sort = {"displayOrder", "name", "id"},
              direction = Sort.Direction.ASC)
          Pageable pageable) {
    validateSort(pageable);

    return PageResponse.from(
        productService.findActiveProducts(pageable).map(PublicProductResponse::from));
  }

  @Operation(
      summary = "Retrieve a public product by its slug",
      description =
          "Returns the details for a single active product that is visible to public users. "
              + "The product is looked up by its unique slug.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Product returned successfully"),
    @ApiResponse(responseCode = "404", description = "Product not found"),
    @ApiResponse(responseCode = "400", description = "Invalid product slug")
  })
  @Validated
  @GetMapping("/{slug}")
  public PublicProductResponse getProduct(
      @Parameter(description = "URL-friendly product identifier", example = "premium-checking")
          @PathVariable
          @Size(max = 80)
          @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$")
          String slug) {
    return PublicProductResponse.from(productService.findActiveProduct(slug));
  }
}
