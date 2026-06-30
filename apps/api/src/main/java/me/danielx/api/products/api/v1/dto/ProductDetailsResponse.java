package me.danielx.api.products.api.v1.dto;

import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import me.danielx.api.products.domain.CreditCardProduct;
import me.danielx.api.products.domain.DepositProduct;
import me.danielx.api.products.domain.Product;

@Schema(
    name = "ProductDetails",
    discriminatorProperty = "kind",
    discriminatorMapping = {
      @DiscriminatorMapping(value = "DEPOSIT", schema = DepositProductDetailsResponse.class),
      @DiscriminatorMapping(
          value = "CREDIT_CARD", schema = CreditCardProductDetailsResponse.class)
    })
public sealed interface ProductDetailsResponse
    permits CreditCardProductDetailsResponse, DepositProductDetailsResponse {
  static ProductDetailsResponse from(Product product) {
    if (product instanceof DepositProduct depositProduct) {
      return new DepositProductDetailsResponse(
          "DEPOSIT",
          depositProduct.getApy(),
          depositProduct.getMinimumDeposit(),
          depositProduct.getMinimumBalance(),
          depositProduct.getMonthlyFee());
    }

    if (product instanceof CreditCardProduct creditCardProduct) {
      return new CreditCardProductDetailsResponse(
          "CREDIT_CARD",
          creditCardProduct.getRewardCategory(),
          creditCardProduct.getRewardRate(),
          creditCardProduct.getSpendingCap());
    }
    throw new IllegalStateException("Unsupported product type: " + product.getClass().getName());
  }
}
