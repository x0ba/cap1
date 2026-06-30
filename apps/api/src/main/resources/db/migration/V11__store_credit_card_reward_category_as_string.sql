ALTER TABLE credit_card_products
    ALTER COLUMN reward_category TYPE VARCHAR(255)
    USING (
        CASE reward_category
            WHEN 0 THEN 'ALL_PURCHASES'
            WHEN 1 THEN 'DINING'
            WHEN 2 THEN 'GROCERIES'
            WHEN 3 THEN 'ENTERTAINMENT'
            WHEN 4 THEN 'TRAVEL'
            WHEN 5 THEN 'GAS'
            WHEN 6 THEN 'STREAMING'
        END
    );
