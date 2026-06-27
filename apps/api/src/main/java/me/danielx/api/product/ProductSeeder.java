package me.danielx.api.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ProductSeeder implements CommandLineRunner {
    private final ProductRepository productRepository;

    public ProductSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            return;
        }

        Instant now = Instant.now();

        Product checking = new Product();
        checking.setSlug("everyday-checking");
        checking.setName("Everyday Checking");
        checking.setShortDescription("A simple checking account for daily spending.");
        checking.setDescription("No-frills checking with debit card access and online banking.");
        checking.setType(ProductType.CHECKING);
        checking.setStatus(ProductStatus.ACTIVE);
        checking.setFeatured(true);
        checking.setApplicationAvailable(true);
        checking.setDisplayOrder(1);
        checking.setCreatedAt(now);
        checking.setUpdatedAt(now);

        Product savings = new Product();
        savings.setSlug("high-yield-savings");
        savings.setName("High-Yield Savings");
        savings.setShortDescription("Grow your savings with a competitive rate.");
        savings.setDescription("A savings account designed for emergency funds and long-term goals.");
        savings.setType(ProductType.SAVINGS);
        savings.setStatus(ProductStatus.ACTIVE);
        savings.setFeatured(true);
        savings.setApplicationAvailable(true);
        savings.setDisplayOrder(2);
        savings.setCreatedAt(now);
        savings.setUpdatedAt(now);

        Product creditCard = new Product();
        creditCard.setSlug("cashback-credit-card");
        creditCard.setName("Cashback Credit Card");
        creditCard.setShortDescription("Earn cashback on everyday purchases.");
        creditCard.setDescription("A rewards credit card with cashback on groceries, gas, and dining.");
        creditCard.setType(ProductType.CREDIT_CARD);
        creditCard.setStatus(ProductStatus.ACTIVE);
        creditCard.setFeatured(false);
        creditCard.setApplicationAvailable(true);
        creditCard.setDisplayOrder(3);
        creditCard.setCreatedAt(now);
        creditCard.setUpdatedAt(now);

        productRepository.save(checking);
        productRepository.save(savings);
        productRepository.save(creditCard);
    }
}