package me.danielx.api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String slug;
    private String name;
    private String shortDescription;
    private String description;
    private ProductType type;
    private ProductStatus status;
    private boolean featured;
    private boolean applicationAvailable;
    private int displayOrder;
    private Instant createdAt;
    private Instant updatedAt;
    private long version;

    public Product() {
    }

    public Product(UUID id, String slug, String name, String shortDescription, String description, ProductType type, ProductStatus status, boolean featured, boolean applicationAvailable, int displayOrder, Instant createdAt, Instant updatedAt, long version) {
        this.id = id;
        this.slug = slug;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.type = type;
        this.status = status;
        this.featured = featured;
        this.applicationAvailable = applicationAvailable;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public void setType(ProductType type) {
        this.type = type;
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

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return featured == product.featured && applicationAvailable == product.applicationAvailable && displayOrder == product.displayOrder && version == product.version && Objects.equals(id, product.id) && Objects.equals(slug, product.slug) && Objects.equals(name, product.name) && Objects.equals(shortDescription, product.shortDescription) && Objects.equals(description, product.description) && type == product.type && status == product.status && Objects.equals(createdAt, product.createdAt) && Objects.equals(updatedAt, product.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, slug, name, shortDescription, description, type, status, featured, applicationAvailable, displayOrder, createdAt, updatedAt, version);
    }
}
