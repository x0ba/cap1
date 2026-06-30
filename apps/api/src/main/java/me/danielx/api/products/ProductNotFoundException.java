package me.danielx.api.products;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException() {
    super("Product not found");
  }
}
