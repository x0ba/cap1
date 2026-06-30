package me.danielx.api.products.api.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import me.danielx.api.products.domain.CreditCardRewardCategory;

@Schema(
    name = "CreditCardProductDetails",
    requiredProperties = {"kind", "rewardCategory", "rewardRate", "spendingCap"})
public record CreditCardProductDetailsResponse(
    @Schema(allowableValues = "CREDIT_CARD") String kind,
    CreditCardRewardCategory rewardCategory,
    BigDecimal rewardRate,
    Long spendingCap)
    implements ProductDetailsResponse {}
