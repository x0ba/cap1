package me.danielx.api.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;
import java.time.Instant;

@Schema(
    name = "ApiProblem",
    description = "RFC 9457 problem details returned when a request fails.")
public record ApiProblemResponse(
    @Schema(
            description = "Short, human-readable summary of the problem.",
            example = "Invalid request",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
    @Schema(
            description = "HTTP status code.",
            example = "400",
            minimum = "400",
            maximum = "599",
            requiredMode = Schema.RequiredMode.REQUIRED)
        int status,
    @Schema(
            description = "Human-readable explanation specific to this occurrence.",
            example = "Request validation failed",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String detail,
    @Schema(
            description = "Request path associated with this occurrence.",
            example = "/api/public/v1/products/INVALID",
            requiredMode = Schema.RequiredMode.REQUIRED)
        URI instance,
    @Schema(
            description = "UTC time when the problem response was created.",
            example = "2026-06-28T18:27:10.150966Z",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Instant timestamp) {}
