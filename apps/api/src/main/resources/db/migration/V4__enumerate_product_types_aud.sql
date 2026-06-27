/* Changes type and status in product_aud table to enumerated types
   instead of ordinal types.
 */

ALTER TABLE products_aud
    ALTER COLUMN type TYPE varchar(50)
        USING (
        CASE type
            WHEN 0 then 'CHECKING'
            WHEN 1 then 'SAVINGS'
            WHEN 2 then 'MONEY_MARKET'
            WHEN 3 then 'CERTIFICATE'
            WHEN 4 then 'CREDIT_CARD'
            ELSE NULL
            END
        );

ALTER TABLE products_aud
    ALTER COLUMN status TYPE varchar(50)
        USING (
        CASE status
            WHEN 0 then 'DRAFT'
            WHEN 1 then 'ACTIVE'
            WHEN 2 then 'PAUSED'
            WHEN 3 then 'RETIRED'
            ELSE NULL
            END
        );
