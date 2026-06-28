package me.danielx.api.products;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "deposit_product_details")
public class DepositProductDetails {
  @Id
  @Column(name = "product_id", nullable = false)
  private Long productId;

  @NotNull
  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "product_id")
  private Product product;

  @NotNull
  @Column(nullable = false, precision = 10, scale = 5)
  private BigDecimal APY;

  @NotNull
  @Column(nullable = false)
  private long minimumDeposit;

  @NotNull
  @Column(nullable = false)
  private long minimumBalance;

  @NotNull
  @Column(nullable = false)
  private long monthlyFee;

  protected DepositProductDetails() {}

  public DepositProductDetails(
      Long productId,
      Product product,
      BigDecimal APY,
      long minimumDeposit,
      long minimumBalance,
      long monthlyFee) {
    this.productId = productId;
    this.product = product;
    this.APY = APY;
    this.minimumDeposit = minimumDeposit;
    this.minimumBalance = minimumBalance;
    this.monthlyFee = monthlyFee;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public BigDecimal getAPY() {
    return APY;
  }

  public void setAPY(BigDecimal APY) {
    this.APY = APY;
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
    DepositProductDetails that = (DepositProductDetails) o;
    return minimumDeposit == that.minimumDeposit
        && minimumBalance == that.minimumBalance
        && monthlyFee == that.monthlyFee
        && Objects.equals(productId, that.productId)
        && Objects.equals(product, that.product)
        && Objects.equals(APY, that.APY);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, product, APY, minimumDeposit, minimumBalance, monthlyFee);
  }
}
