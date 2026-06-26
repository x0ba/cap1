package me.danielx.api.dto;

import me.danielx.api.Product;
import me.danielx.api.ProductType;

import java.util.UUID;

public record PublicProductResponse(
        UUID id,
        String slug,
        String name,
        String shortDescription,
        ProductType type,
        boolean featured,
        boolean applicationAvailable
) {
    public static PublicProductResponse from(Product product) {
        return new PublicProductResponse(
                product.getId(),
                product.getSlug(),
                product.getName(),
                product.getShortDescription(),
                product.getType(),
                product.isFeatured(),
                product.isApplicationAvailable()
        );
    }
}
