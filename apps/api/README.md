# Capital Two API

Lazy README for the Spring Boot API app.

## Disclaimer

This project is ongoing and very much not final. The API surface, data model, naming, and local setup may change as the rest of the app gets built out. Treat this README as a quick map of what exists right now, not a full product contract.

## What It Is

This is the backend API for Capital Two product discovery. It currently focuses on public read-only product endpoints backed by PostgreSQL, JPA, and Flyway migrations.

The app is built with:

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Flyway
- Redis-backed Spring Session dependency
- Springdoc OpenAPI / Swagger UI
- Maven

## Functionality So Far

The API currently supports public product browsing:

- List active public products.
- Fetch one active public product by slug.
- Return paginated list responses with `items`, `page`, `size`, `totalItems`, `totalPages`, and `hasNext`.
- Validate list sorting so only `displayOrder`, `name`, and `id` are accepted.
- Normalize product slugs to lowercase on lookup.
- Return RFC 9457-style problem details for validation errors, missing products, invalid request parameters, and unexpected errors.
- Generate OpenAPI docs for the public product API.

Supported product data currently includes:

- Common product fields: `slug`, `name`, `shortDescription`, `description`, `type`, `featured`, and `applicationAvailable`.
- Deposit product details: `apy`, `minimumDeposit`, `minimumBalance`, and `monthlyFee`.
- Credit card product details: `rewardCategory`, `rewardRate`, and `spendingCap`.

Current product types:

- `CHECKING`
- `SAVINGS`
- `MONEY_MARKET`
- `CERTIFICATE`
- `CREDIT_CARD`

## Endpoints

Base public product path:

```text
/api/public/v1/products
```

### List Products

```http
GET /api/public/v1/products
```

Returns active products only.

Useful query params:

- `page`: zero-based page number.
- `size`: page size, capped by Spring config at `100`.
- `sort`: accepted fields are `displayOrder`, `name`, and `id`.

Default sort:

```text
displayOrder,asc
name,asc
id,asc
```

### Get Product

```http
GET /api/public/v1/products/{slug}
```

Returns one active product by slug. Slugs must match:

```text
^[a-z0-9]+(?:-[a-z0-9]+)*$
```

Example:

```http
GET /api/public/v1/products/premium-checking
```

## Local Setup

Start the local database and Redis:

```sh
docker compose up -d
```

Create local environment values from the example:

```sh
cp src/.env.example .env.local
```

For the provided `compose.yml`, the values are typically:

```sh
DATABASE_URL=jdbc:postgresql://localhost:5432/cap2
DATABASE_USERNAME=cap2
DATABASE_PASSWORD=password
```

Run the app:

```sh
./mvnw spring-boot:run
```

Run tests:

```sh
./mvnw test
```

Run formatting checks:

```sh
./mvnw spotless:check
```

## Docs

When the app is running, OpenAPI docs should be available at:

```text
http://localhost:8080/v3/api-docs
http://localhost:8080/swagger-ui.html
```

## Notes

- Flyway owns schema migrations under `src/main/resources/db/migration`.
- Hibernate is configured with `ddl-auto=validate`, so the database schema needs to match the migrations.
- There is no public write/admin API exposed yet, even though some admin request DTOs exist.
- The API only returns products with `ACTIVE` status from the public endpoints.
