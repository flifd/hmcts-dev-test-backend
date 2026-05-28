# Task Manager API

A Spring Boot REST API backend for managing tasks, backed by a PostgreSQL database.

---

## Prerequisites

- Java 21+
- Gradle 7.5+
- PostgreSQL 13+

---

## Database Setup

### 1. Create the database

Connect to your PostgreSQL instance and create a database for the application:

```sql
CREATE DATABASE taskmanager;
```

### 2. Apply the schema

Run the provided `schema.sql` file against your new database:

```bash
psql -U <your_username> -d taskmanager -f schema.sql
```

Or paste the contents directly into your PostgreSQL client. The schema creates the following table:

| Column              | Type           | Notes                        |
|---------------------|----------------|------------------------------|
| `id`                | `integer`      | Primary key, auto-generated  |
| `title`             | `varchar`      | Required                     |
| `description`       | `varchar`      | Optional                     |
| `status`            | `varchar`      | Required                     |
| `due_timestamp`     | `timestamptz`  | Required                     |
| `created_timestamp` | `timestamptz`  | Required                     |
| `updated_timestamp` | `timestamptz`  | Required                     |
---

## Configuration

The application reads its database connection from `src/main/resources/application.properties`.

A template is provided at `application.properties.example` — copy it and fill in your values:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Then edit `application.yaml` to set your database credentials: or configure the required environment variables

```yaml
        url: jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
        username: ${DB_USER_NAME}
        password: ${DB_PASSWORD}
```

---

## Running the Application

You should be able to run `./gradlew build` to start with to ensure it builds successfully. Then from that you
can run the service in IntelliJ (or your IDE of choice) or however you normally would.

The API will start on `http://localhost:4000` by default.

---
