package me.danielx.api.products;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "credit_card_products")
@DiscriminatorValue("CREDIT_CARD")
public class CreditCardProduct extends Product {
  @NotNull
  @Column(nullable = false)
  private CreditCardRewardCategory rewardCategory;

  @NotNull
  @Column(nullable = false, precision = 6, scale = 3)
  private BigDecimal rewardRate;

  @NotNull
  @Column(nullable = false)
  private Long spendingCap;

  protected CreditCardProduct() {}

  public CreditCardProduct(
      String slug,
      String name,
      String shortDescription,
      String description,
      ProductStatus status,
      boolean featured,
      boolean applicationAvailable,
      int displayOrder,
      CreditCardRewardCategory rewardCategory,
      BigDecimal rewardRate,
      Long spendingCap) {
    super(
        slug,
        name,
        shortDescription,
        description,
        ProductType.CREDIT_CARD,
        status,
        featured,
        applicationAvailable,
        displayOrder);
    this.rewardCategory = rewardCategory;
    this.rewardRate = rewardRate;
    this.spendingCap = spendingCap;
  }
}
