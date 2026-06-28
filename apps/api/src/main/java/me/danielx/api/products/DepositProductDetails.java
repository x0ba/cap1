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

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @NotNull
  @Column(nullable = false, precision = 10, scale = 5)
  private BigDecimal apy;

  @Column(nullable = false)
  private long minimumDeposit;

  @Column(nullable = false)
  private long minimumBalance;

  @Column(nullable = false)
  private long monthlyFee;

  protected DepositProductDetails() {}

  public DepositProductDetails(
      BigDecimal apy, long minimumDeposit, long minimumBalance, long monthlyFee) {
    this.apy = Objects.requireNonNull(apy);
    this.minimumDeposit = minimumDeposit;
    this.minimumBalance = minimumBalance;
    this.monthlyFee = monthlyFee;
  }

  public Long getProductId() {
    return productId;
  }

  public Product getProduct() {
    return product;
  }

  void setProduct(Product product) {
    if (this.product != null && product != null && this.product != product) {
      throw new IllegalStateException("Details are already assigned to another product");
    }

    this.product = product;
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
    DepositProductDetails that = (DepositProductDetails) o;
    return minimumDeposit == that.minimumDeposit
        && minimumBalance == that.minimumBalance
        && monthlyFee == that.monthlyFee
        && Objects.equals(productId, that.productId)
        && Objects.equals(product, that.product)
        && Objects.equals(apy, that.apy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, product, apy, minimumDeposit, minimumBalance, monthlyFee);
  }
}
