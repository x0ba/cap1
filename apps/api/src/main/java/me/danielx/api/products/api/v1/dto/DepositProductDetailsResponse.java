package me.danielx.api.products.api.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(
    name = "DepositProductDetails",
    requiredProperties = {"kind", "apy", "minimumDeposit", "minimumBalance", "monthlyFee"})
public record DepositProductDetailsResponse(
    @Schema(allowableValues = "DEPOSIT") String kind,
    BigDecimal apy,
    Long minimumDeposit,
    Long minimumBalance,
    Long monthlyFee)
    implements ProductDetailsResponse {}
