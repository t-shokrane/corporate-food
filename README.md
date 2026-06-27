# Corporate Food API — Enterprise Template

Production-grade Spring Boot base template for the **Corporate Food Ordering System**.

Senior developers define architecture here; junior developers extend features by following the **Company module** reference implementation.

## Stack

- Java 25 · Spring Boot 4.1.0
- Spring Data JPA / Hibernate (soft delete + auditing, `ddl-auto=validate`)
- MySQL 8 · Flyway (single baseline migration)
- Lombok · MapStruct · Thymeleaf (admin skeleton)

## Quick Start

**Option A — Docker (recommended)**

```bash
docker compose up -d
mvn spring-boot:run
```

**Option B — local MySQL**

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS corporate_food CHARACTER SET utf8mb4 COLLATE utf8mb4_persian_ci;"
mvn spring-boot:run
```

The datasource URL includes `createDatabaseIfNotExist=true`, so MySQL will create `corporate_food` automatically when the server is reachable.

- REST API: `http://localhost:8080/api/v1`
- Admin skeleton: `http://localhost:8080/admin`

## Database (Flyway — Single Source of Truth)

All schema and initial seed data are defined in **one** migration file:

```
src/main/resources/db/migration/V1__init_schema.sql
```

Rules:

- Flyway is the **only** way to change the database schema
- Initial delivery contains **only V1** (DDL + seed INSERTs) — no parallel versions
- Hibernate must **never** create or alter tables (`spring.jpa.hibernate.ddl-auto=validate`)
- Future schema changes: add `V2`, `V3`, … only when absolutely required

For a fresh database, drop and recreate `corporate_food` if an older multi-version history exists locally.

## Package Structure

```
com.corporate.food
├── config          # Security, JPA auditing
├── controller      # REST + BaseController
├── service         # Business logic + BaseService
├── repository      # JPA + BaseRepository
├── domain
│   ├── BaseDomain  # id, is_deleted, version, created_on, updated_on
│   ├── entity      # JPA entities
│   └── enums
├── dto             # Request/Response + ApiResponse
├── exception       # Custom exceptions + GlobalExceptionHandler
├── mapper          # Entity/DTO mappers
└── util            # Shared utilities (if needed)
```

## Reference Module

**Company** (`/api/v1/companies`) is the fully implemented end-to-end template. All other service modules are skeleton-only (TODO stubs for juniors).

## API Response Format

Success:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { },
  "timestamp": "2026-06-17T10:00:00"
}
```

Error:

```json
{
  "timestamp": "2026-06-17T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Company not found: 99",
  "path": "/api/v1/companies/99"
}
```

## How Juniors Should Continue Development

### Implementing a skeleton module (e.g. `Food`)

1. **Service** — replace TODO stubs with business logic; extend `BaseService` and use `toPageable(filter)` for pagination
2. **Controller** — extend `BaseController`; use `ok()`, `created()`, and `deleted()` for responses
3. Use `ResourceNotFoundException`, `BadRequestException`, `ValidationException` from the exception package
4. Never expose entities from controllers — use Request/Response DTOs

### Adding a new module (e.g. `reports`)

1. **Entity** — extend `BaseDomain` in `domain/entity/`
2. **Repository** — extend `BaseRepository<Entity, Long>`
3. **DTOs** — `{Module}Request` and `{Module}Response` with Jakarta validation
4. **Mapper** — dedicated mapper class (see `EntityMapper` / `FoodMapper`)
5. **Service** — extend `BaseService`; use `toPageable(filter)` for all list queries (default page size: 10)
6. **Controller** — extend `BaseController`, map to `/api/v1/{module-plural}`
7. **Migration** — add `V{n}__description.sql` under `db/migration` (senior review required)

### Naming rules

| Layer | Convention | Example |
|-------|------------|---------|
| Entity | PascalCase, singular | `Food` |
| Repository | `{Entity}Repository` | `FoodRepository` |
| Service | `{Entity}Service` | `FoodService` |
| Controller | `{Entity}Controller` | `FoodController` |
| Request DTO | `{Entity}Request` | `FoodRequest` |
| Response DTO | `{Entity}Response` | `FoodResponse` |
| API path | kebab-case plural | `/api/v1/foods` |

### Error handling

- `ResourceNotFoundException` → 404
- `BadRequestException` / `ValidationException` / `BusinessException` → 400
- `@Valid` on request bodies → 400 with field message

### DTO rules

- Never expose entities directly from controllers
- Use Request DTOs for input, Response DTOs for output
- Put validation annotations on Request DTOs only
