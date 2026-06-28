package me.danielx.api.common.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "Capital Two API",
            version = "v1",
            description =
                "Public HTTP API for Capital Two product discovery. Error responses use RFC 9457 problem details."),
    tags =
        @Tag(
            name = "Public Products",
            description = "Publicly visible financial products and product details."))
public class OpenApiConfiguration {}
