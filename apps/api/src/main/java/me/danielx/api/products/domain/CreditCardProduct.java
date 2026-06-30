package me.danielx.api.products.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "credit_card_products")
@DiscriminatorValue("CREDIT_CARD")
public class CreditCardProduct extends Product {
  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
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

  public CreditCardRewardCategory getRewardCategory() {
    return rewardCategory;
  }

  public void setRewardCategory(CreditCardRewardCategory rewardCategory) {
    this.rewardCategory = rewardCategory;
  }

  public BigDecimal getRewardRate() {
    return rewardRate;
  }

  public void setRewardRate(BigDecimal rewardRate) {
    this.rewardRate = rewardRate;
  }

  public Long getSpendingCap() {
    return spendingCap;
  }

  public void setSpendingCap(Long spendingCap) {
    this.spendingCap = spendingCap;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    CreditCardProduct that = (CreditCardProduct) o;
    return rewardCategory == that.rewardCategory
        && Objects.equals(rewardRate, that.rewardRate)
        && Objects.equals(spendingCap, that.spendingCap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), rewardCategory, rewardRate, spendingCap);
  }
}
