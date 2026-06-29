package me.danielx.api.products.api.v1.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.danielx.api.products.domain.ProductStatus;
import me.danielx.api.products.domain.ProductType;

public record CreateProductRequest(
    @NotBlank(message = "Slug is required") String slug,
    @NotBlank(message = "Name is required") String name,
    String shortDescription,
    String description,
    @NotNull(message = "Type is required") ProductType type,
    @NotNull(message = "Status is required") ProductStatus status,
    @NotNull(message = "Featured is required") Boolean featured,
    @NotNull(message = "Application available is required") Boolean applicationAvailable,
    @NotNull(message = "Display order is required") Integer displayOrder) {}
