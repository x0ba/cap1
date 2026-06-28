package me.danielx.api.product.dto;

import me.danielx.api.product.Product;
import me.danielx.api.product.ProductType;

public record PublicProductResponse(
    Long id,
    String slug,
    String name,
    String shortDescription,
    String description,
    ProductType type,
    boolean featured,
    boolean applicationAvailable) {
  public static PublicProductResponse from(Product product) {
    return new PublicProductResponse(
        product.getId(),
        product.getSlug(),
        product.getName(),
        product.getShortDescription(),
        product.getDescription(),
        product.getType(),
        product.isFeatured(),
        product.isApplicationAvailable());
  }
}
