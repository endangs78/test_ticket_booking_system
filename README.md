# 🎟 Concert Ticket Booking System

Enterprise-ready backend service for managing concerts, ticket bookings, dynamic pricing, and revenue analytics.
Built using **Spring Boot + PostgreSQL** with transactional consistency and concurrency safety.

---

## 🚀 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- JUnit 5 & Mockito
- Maven

---

## 🏗 Architecture

Layered architecture:

Controller → Service → Repository → Database

Design principles:
- Transactional boundary on critical operations
- Pessimistic locking to prevent overselling
- Idempotency key for duplicate booking protection
- Centralized exception handling
- BigDecimal for financial accuracy

---

## 📦 Core Features

### 🎤 Concert Management
- Create / Update Concert
- Retrieve Concert List / Detail

### 🎫 Booking System
- Idempotent booking
- Stock validation with locking
- Booking cancellation
- Automatic price calculation

### 💰 Dynamic Pricing
Price adjusts based on demand:
demand = 1 - (availableStock / totalStock)
price = basePrice × (1 + demand) × categoryMultiplier

### 📊 Settlement & Analytics
- Revenue per concert
- Booking summary
- Global dashboard metrics
- Transaction listing

---

## 🗄 Database Overview

Main tables:
- `concerts`
- `ticket_category`
- `booking`
- `transaction`

Primary keys use `BIGSERIAL`.
Financial fields use `NUMERIC` for precision.

---

## 🔐 Concurrency & Consistency

- `@Transactional`
- `@Lock(PESSIMISTIC_WRITE)`
- Unique idempotency key
- Prevents overselling and duplicate booking

---

## 🧪 Testing

- Unit tests for all service layers
- Mockito-based repository mocking
- Financial comparison using BigDecimal safe assertions

