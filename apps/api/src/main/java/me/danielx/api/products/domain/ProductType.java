package me.danielx.api.products.domain;

public enum ProductType {
  CHECKING,
  SAVINGS,
  MONEY_MARKET,
  CERTIFICATE,
  CREDIT_CARD;

  public boolean isDeposit() {
    return switch (this) {
      case CHECKING, SAVINGS, MONEY_MARKET, CERTIFICATE -> true;
      case CREDIT_CARD -> false;
    };
  }
}
