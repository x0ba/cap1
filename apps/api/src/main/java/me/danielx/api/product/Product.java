package me.danielx.api.product;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(
                        name = "idx_products_id",
                        columnList = "id"
                )
        }
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String shortDescription;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private ProductType type;

    @Column(nullable = false)
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

    @Version
    @Column(nullable = false)
    private long version;

    protected Product() {
    }

    public Product(Long id, String slug, String name, String shortDescription, String description, ProductType type, ProductStatus status, boolean featured, boolean applicationAvailable, int displayOrder, Instant createdAt, Instant updatedAt, long version) {
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
