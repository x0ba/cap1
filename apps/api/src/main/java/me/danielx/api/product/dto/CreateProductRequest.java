package me.danielx.api.product.dto;

import jakarta.validation.constraints.NotBlank;
import me.danielx.api.product.ProductStatus;
import me.danielx.api.product.ProductType;

public record CreateProductRequest(
        @NotBlank(message = "Slug is required")
        String slug,

        @NotBlank(message = "Name is required")
        String name,

        String shorDescription,
        String description,

        @NotBlank(message = "Type is required")
        ProductType type,

        @NotBlank(message = "Status is required")
        ProductStatus status,

        @NotBlank(message = "Featured is required")
        boolean featured,

        @NotBlank(message = "Application available is required")
        boolean applicationAvailable,

        @NotBlank(message = "Display order is required")
        int displayOrder
) {

}
