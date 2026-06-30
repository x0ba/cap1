package me.danielx.api.products.api.v1.dto;

import me.danielx.api.products.domain.Product;
import me.danielx.api.products.domain.ProductType;

public record PublicProductSummaryResponse(
    Long id,
    String slug,
    String name,
    String shortDescription,
    ProductType type,
    boolean featured,
    boolean applicationAvailable) {
  public static PublicProductSummaryResponse from(Product product) {
    return new PublicProductSummaryResponse(
        product.getId(),
        product.getSlug(),
        product.getName(),
        product.getShortDescription(),
        product.getType(),
        product.isFeatured(),
        product.isApplicationAvailable());
  }
}
