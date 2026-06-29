package me.danielx.api.products.application;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException() {
    super("Product not found");
  }
}
