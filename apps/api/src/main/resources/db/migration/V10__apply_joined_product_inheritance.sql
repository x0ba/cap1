DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM products p
        WHERE p.type IN ('CHECKING', 'SAVINGS', 'MONEY_MARKET', 'CERTIFICATE')
          AND NOT EXISTS (
              SELECT 1
              FROM deposit_product_details d
              WHERE d.product_id = p.id
          )
    ) THEN
        RAISE EXCEPTION
            'Cannot migrate: every existing deposit product must have deposit details';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM products
        WHERE type = 'CREDIT_CARD'
    ) THEN
        RAISE EXCEPTION
            'Cannot migrate existing credit-card products without reward data';
    END IF;
END;
$$;

ALTER TABLE deposit_product_details
    RENAME TO deposit_products;

ALTER TABLE deposit_products
    RENAME COLUMN product_id TO id;

ALTER TABLE deposit_products
    RENAME CONSTRAINT pk_deposit_product_details TO pk_deposit_products;

ALTER TABLE deposit_products
    RENAME CONSTRAINT fk_deposit_product_details_product TO fk_deposit_products_product;

CREATE TABLE credit_card_products
(
    id              BIGINT        NOT NULL,
    reward_category SMALLINT      NOT NULL,
    reward_rate     NUMERIC(6, 3) NOT NULL,
    spending_cap    BIGINT        NOT NULL,
    CONSTRAINT pk_credit_card_products PRIMARY KEY (id),
    CONSTRAINT fk_credit_card_products_product
        FOREIGN KEY (id)
            REFERENCES products (id)
);

ALTER TABLE products
    ADD COLUMN entity_kind VARCHAR(31);

UPDATE products
SET entity_kind = CASE
    WHEN type IN ('CHECKING', 'SAVINGS', 'MONEY_MARKET', 'CERTIFICATE') THEN 'DEPOSIT'
    WHEN type = 'CREDIT_CARD' THEN 'CREDIT_CARD'
END;

ALTER TABLE products
    ALTER COLUMN entity_kind SET NOT NULL,
    DROP COLUMN version,
    ADD CONSTRAINT chk_products_entity_kind_type
        CHECK (
            (entity_kind = 'DEPOSIT'
                AND type IN ('CHECKING', 'SAVINGS', 'MONEY_MARKET', 'CERTIFICATE'))
            OR (entity_kind = 'CREDIT_CARD' AND type = 'CREDIT_CARD')
        );
