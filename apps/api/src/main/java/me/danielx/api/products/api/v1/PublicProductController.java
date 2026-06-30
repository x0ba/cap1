package me.danielx.api.products.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;

import me.danielx.api.common.dto.ApiProblemResponse;
import me.danielx.api.common.dto.PageResponse;
import me.danielx.api.products.api.v1.dto.PublicProductDetailResponse;
import me.danielx.api.products.api.v1.dto.PublicProductSummaryResponse;
import me.danielx.api.products.PublicProductQueryService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "Public Products")
@RestController
@RequestMapping(value = "/api/public/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicProductController {
  private final PublicProductQueryService productQueryService;
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

  public PublicProductController(PublicProductQueryService productQueryService) {
    this.productQueryService = productQueryService;
  }

  @Operation(
      operationId = "listPublicProducts",
      summary = "List public products",
      description =
          "Returns a paginated list of active products that are visible to public users. "
              + "Products are sorted by display order, name, and ID by default.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Products returned successfully"),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid pagination or sort parameter",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                schema = @Schema(implementation = ApiProblemResponse.class)))
  })
  @GetMapping
  public PageResponse<PublicProductSummaryResponse> listProducts(
      @ParameterObject
          @PageableDefault(
              size = 20,
              sort = {"displayOrder", "name", "id"},
              direction = Sort.Direction.ASC)
          Pageable pageable) {
    validateSort(pageable);

    return PageResponse.from(
        productQueryService.findActiveProducts(pageable).map(PublicProductSummaryResponse::from));
  }

  @Operation(
      operationId = "getPublicProduct",
      summary = "Retrieve a public product by its slug",
      description =
          "Returns the details for a single active product that is visible to public users. "
              + "The product is looked up by its unique slug.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Product returned successfully"),
    @ApiResponse(
        responseCode = "404",
        description = "Product not found",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                schema = @Schema(implementation = ApiProblemResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid product slug",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                schema = @Schema(implementation = ApiProblemResponse.class)))
  })
  @Validated
  @GetMapping("/{slug}")
  public PublicProductDetailResponse getProduct(
      @Parameter(description = "URL-friendly product identifier", example = "premium-checking")
          @PathVariable
          @Size(max = 80)
          @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$")
          String slug) {
    return PublicProductDetailResponse.from(productQueryService.findActiveProduct(slug));
  }
}
