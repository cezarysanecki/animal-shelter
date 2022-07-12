# Writing integration tests

## Status

Accepted

## Context

We need to decide how we will test our integration layer in application in context of database.
On production, we use MySQL, so we consider the below solutions:
- H2
- Testcontainers with MySQL

## Decision
Test database: H2

We decided to use H2, because in our application so far there are no such complicated SQL queries and they 
are not depended on specified DBMS.

## Consequences

### Pros
Tests are much quicker.

### Cons
We might not find bug in specific queries for MySQL.