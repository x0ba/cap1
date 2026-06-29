package me.danielx.api.products.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(
    name = "products",
    indexes = {
      @Index(name = "idx_products_public_listing", columnList = "status, displayOrder, name, id")
    })
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "entity_kind")
public abstract class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Slug is required") @Size(max = 80) @Column(nullable = false, length = 80, unique = true)
  private String slug;

  @NotBlank(message = "Name is required") @Size(max = 120) @Column(nullable = false, length = 120)
  private String name;

  @Size(max = 300) @Column(length = 300)
  private String shortDescription;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false, name = "type")
  @Enumerated(EnumType.STRING)
  private ProductType type;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ProductStatus status;

  @Column(nullable = false)
  private boolean featured;

  @Column(nullable = false)
  private boolean applicationAvailable;

  @Column(nullable = true)
  private int displayOrder = 0;

  @Column(nullable = false)
  private Instant createdAt;

  @Column(nullable = true)
  private Instant updatedAt;

  protected Product() {}

  public Product(
      String slug,
      String name,
      String shortDescription,
      String description,
      ProductType type,
      ProductStatus status,
      boolean featured,
      boolean applicationAvailable,
      int displayOrder) {
    this.slug = slug;
    this.name = name;
    this.shortDescription = shortDescription;
    this.description = description;
    this.type = Objects.requireNonNull(type);
    this.status = status;
    this.featured = featured;
    this.applicationAvailable = applicationAvailable;
    this.displayOrder = displayOrder;
    this.createdAt = Instant.now();
    this.updatedAt = null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ProductType getType() {
    return type;
  }

  public ProductStatus getStatus() {
    return status;
  }

  public void setStatus(ProductStatus status) {
    this.status = status;
  }

  public boolean isFeatured() {
    return featured;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public boolean isApplicationAvailable() {
    return applicationAvailable;
  }

  public void setApplicationAvailable(boolean applicationAvailable) {
    this.applicationAvailable = applicationAvailable;
  }

  public int getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(int displayOrder) {
    this.displayOrder = displayOrder;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return featured == product.featured
        && applicationAvailable == product.applicationAvailable
        && displayOrder == product.displayOrder
        && Objects.equals(id, product.id)
        && Objects.equals(slug, product.slug)
        && Objects.equals(name, product.name)
        && Objects.equals(shortDescription, product.shortDescription)
        && Objects.equals(description, product.description)
        && type == product.type
        && status == product.status
        && Objects.equals(createdAt, product.createdAt)
        && Objects.equals(updatedAt, product.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        slug,
        name,
        shortDescription,
        description,
        type,
        status,
        featured,
        applicationAvailable,
        displayOrder,
        createdAt,
        updatedAt);
  }
}
