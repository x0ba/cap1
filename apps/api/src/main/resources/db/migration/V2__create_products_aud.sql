CREATE TABLE products_aud
(
    id                    BIGINT   NOT NULL,
    rev                   BIGINT   NOT NULL,
    revtype               SMALLINT,
    slug                  VARCHAR(255),
    name                  VARCHAR(255),
    short_description     VARCHAR(255),
    description           VARCHAR(255),
    type                  SMALLINT,
    status                SMALLINT,
    featured              BOOLEAN,
    application_available BOOLEAN,
    display_order         INTEGER,
    created_at            TIMESTAMP WITHOUT TIME ZONE,
    updated_at            TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_products_aud PRIMARY KEY (rev, id)
);

ALTER TABLE products_aud
    ADD CONSTRAINT fk_products_aud_on_revinfo FOREIGN KEY (rev) REFERENCES revinfo (rev);
