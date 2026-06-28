DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM products WHERE length(slug) > 80
        OR length(name) > 120
        OR length(short_description) > 300
    ) THEN
        RAISE EXCEPTION 'Text column length exceeded';
    END IF;
END;
$$;

ALTER TABLE products
    ALTER COLUMN slug TYPE varchar(80),
    ALTER COLUMN name TYPE varchar(120),
    ALTER COLUMN short_description TYPE varchar(300),
    ALTER COLUMN description TYPE text,
    ADD CONSTRAINT uk_products_slug UNIQUE (slug);

ALTER TABLE products_aud
    ALTER COLUMN slug TYPE varchar(80),
    ALTER COLUMN name TYPE varchar(120),
    ALTER COLUMN short_description TYPE varchar(300),
    ALTER COLUMN description TYPE text,
    ADD CONSTRAINT uk_products_aud_slug UNIQUE (slug);
