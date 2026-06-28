CREATE TABLE deposit_product_details
(
    product_id      BIGINT         NOT NULL,
    apy             NUMERIC(10, 5) NOT NULL,
    minimum_deposit BIGINT         NOT NULL,
    minimum_balance BIGINT         NOT NULL,
    monthly_fee     BIGINT         NOT NULL,
    CONSTRAINT pk_deposit_product_details PRIMARY KEY (product_id),
    CONSTRAINT fk_deposit_product_details_product
        FOREIGN KEY (product_id)
            REFERENCES products (id)
);
