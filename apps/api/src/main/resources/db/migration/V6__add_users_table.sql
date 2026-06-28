CREATE TABLE users
(
    id                    VARCHAR(36)  NOT NULL,
    email                 VARCHAR(120) NOT NULL,
    email_verified        BOOLEAN      NOT NULL,
    display_name          VARCHAR(120),
    phone_number          VARCHAR(32),
    phone_number_verified BOOLEAN      NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

CREATE UNIQUE INDEX idx_users_email ON users (email);

CREATE UNIQUE INDEX idx_users_phone_number ON users (phone_number);

CREATE TABLE users_aud
(
    id                    VARCHAR(36) NOT NULL,
    rev                   BIGINT      NOT NULL,
    revtype               SMALLINT,

    email                 VARCHAR(120),
    email_verified        BOOLEAN,
    display_name          VARCHAR(120),
    phone_number          VARCHAR(32),
    phone_number_verified BOOLEAN,

    CONSTRAINT pk_users_aud PRIMARY KEY (rev, id)
);

ALTER TABLE users_aud
    ADD CONSTRAINT fk_users_aud_on_revinfo FOREIGN KEY (rev) REFERENCES revinfo (rev);