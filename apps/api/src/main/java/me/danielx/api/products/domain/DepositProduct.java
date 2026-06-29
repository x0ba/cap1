package me.danielx.api.products.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "deposit_products")
@DiscriminatorValue("DEPOSIT")
public class DepositProduct extends Product {

  @NotNull @Column(nullable = false, precision = 10, scale = 5)
  private BigDecimal apy;

  @Column(nullable = false)
  private long minimumDeposit;

  @Column(nullable = false)
  private long minimumBalance;

  @Column(nullable = false)
  private long monthlyFee;

  protected DepositProduct() {}

  public DepositProduct(
      String slug,
      String name,
      String shortDescription,
      String description,
      ProductType type,
      ProductStatus status,
      boolean featured,
      boolean applicationAvailable,
      int displayOrder,
      BigDecimal apy,
      long minimumDeposit,
      long monthlyFee,
      long minimumBalance) {
    super(
        slug,
        name,
        shortDescription,
        description,
        requireDepositType(type),
        status,
        featured,
        applicationAvailable,
        displayOrder);
    this.apy = Objects.requireNonNull(apy);
    this.minimumDeposit = minimumDeposit;
    this.monthlyFee = monthlyFee;
    this.minimumBalance = minimumBalance;
  }

  private static ProductType requireDepositType(ProductType type) {
    Objects.requireNonNull(type, "Product type is required");

    if (!type.isDeposit()) {
      throw new IllegalArgumentException(type + " is not a deposit product type");
    }

    return type;
  }

  public BigDecimal getApy() {
    return apy;
  }

  public void setApy(BigDecimal apy) {
    this.apy = Objects.requireNonNull(apy);
  }

  public long getMinimumDeposit() {
    return minimumDeposit;
  }

  public void setMinimumDeposit(long minimumDeposit) {
    this.minimumDeposit = minimumDeposit;
  }

  public long getMinimumBalance() {
    return minimumBalance;
  }

  public void setMinimumBalance(long minimumBalance) {
    this.minimumBalance = minimumBalance;
  }

  public long getMonthlyFee() {
    return monthlyFee;
  }

  public void setMonthlyFee(long monthlyFee) {
    this.monthlyFee = monthlyFee;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    DepositProduct that = (DepositProduct) o;
    return minimumDeposit == that.minimumDeposit
        && minimumBalance == that.minimumBalance
        && monthlyFee == that.monthlyFee
        && Objects.equals(apy, that.apy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apy, minimumDeposit, minimumBalance, monthlyFee);
  }
}
