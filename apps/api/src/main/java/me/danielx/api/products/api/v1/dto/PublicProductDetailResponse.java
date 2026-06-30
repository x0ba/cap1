package me.danielx.api.products.api.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import me.danielx.api.products.domain.Product;
import me.danielx.api.products.domain.ProductType;

public record PublicProductDetailResponse(
    Long id,
    String slug,
    String name,
    String shortDescription,
    String description,
    ProductType type,
    boolean featured,
    boolean applicationAvailable,
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            oneOf = {
              DepositProductDetailsResponse.class,
              CreditCardProductDetailsResponse.class
            })
        ProductDetailsResponse productDetails) {
  public static PublicProductDetailResponse from(Product product) {
    return new PublicProductDetailResponse(
        product.getId(),
        product.getSlug(),
        product.getName(),
        product.getShortDescription(),
        product.getDescription(),
        product.getType(),
        product.isFeatured(),
        product.isApplicationAvailable(),
        ProductDetailsResponse.from(product));
  }
}
