# Banking Transaction System

A **Java-based banking application** built with Maven that simulates core banking operations through a **Command-Line Interface (CLI)**. The project is designed with **clean architecture principles**, separating services, repositories, and models for maintainability and testability.

---

## Features

- **User Authentication**
    - Register new customers
    - Login and logout
    - Change password

- **Account Management**
    - Create Savings or Current accounts
    - View account balance
    - Close accounts

- **Transactions**
    - Deposit funds
    - Withdraw funds
    - Transfer money between accounts
    - Mini statement generation

- **Audit & Logging**
    - Transaction audit logs stored in relational DB
    - Optional DynamoDB integration for audit logs

- **Testing**
    - Unit tests written with **JUnit 5**
    - Mockito used for mocking dependencies
    - Tests cover AuthService, AccountService, and TransactionService

- **CI/CD Ready**
    - GitHub Actions for **Continuous Integration** (build + test)

[//]: # (    - Docker image publishing &#40;ready for Continuous Deployment&#41;)

---