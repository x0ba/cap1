package me.danielx.api.products;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class ProductDetailsTest {

  @Test
  void assignsAndRemovesDepositDetailsOnBothSides() {
    Product product = product(ProductType.SAVINGS);
    DepositProductDetails details = details();

    product.setDepositDetails(details);

    assertSame(details, product.getDepositDetails());
    assertSame(product, details.getProduct());

    product.removeDepositDetails();

    assertNull(product.getDepositDetails());
    assertNull(details.getProduct());
  }

  @Test
  void rejectsDepositDetailsForNonDepositProducts() {
    Product product = product(ProductType.CREDIT_CARD);

    assertThrows(IllegalStateException.class, () -> product.setDepositDetails(details()));
    assertNull(product.getDepositDetails());
  }

  @Test
  void rejectsChangingAProductWithDepositDetailsToAnIncompatibleType() {
    Product product = product(ProductType.SAVINGS);
    product.setDepositDetails(details());

    assertThrows(IllegalStateException.class, () -> product.setType(ProductType.CREDIT_CARD));
    assertSame(ProductType.SAVINGS, product.getType());
  }

  @Test
  void rejectsMovingDetailsBetweenProducts() {
    Product firstProduct = product(ProductType.SAVINGS);
    Product secondProduct = product(ProductType.CHECKING);
    DepositProductDetails details = details();
    firstProduct.setDepositDetails(details);

    assertThrows(IllegalStateException.class, () -> secondProduct.setDepositDetails(details));
    assertSame(details, firstProduct.getDepositDetails());
    assertSame(firstProduct, details.getProduct());
    assertNull(secondProduct.getDepositDetails());
  }

  private static Product product(ProductType type) {
    return new Product(
        null,
        "test-product-" + type.name().toLowerCase(),
        "Test Product",
        "Short description",
        "Description",
        type,
        ProductStatus.ACTIVE,
        false,
        true,
        0,
        Instant.EPOCH,
        null,
        0);
  }

  private static DepositProductDetails details() {
    return new DepositProductDetails(new BigDecimal("4.25000"), 100, 500, 0);
  }
}
